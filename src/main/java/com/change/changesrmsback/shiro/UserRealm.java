package com.change.changesrmsback.shiro;

import com.change.changesrmsback.entity.User;
import com.change.changesrmsback.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户认证域
 * @author Change
 */
public class UserRealm extends AuthorizingRealm {

    /** 用户的数据访问层 */
    private UserMapper userMapper;

    /** 用户的数据访问层注入 */
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 在创建认证信息的时候用到，并且在授权时也会获取该name
     * @return 返回"UserRealm"
     */
    @Override
    public String getName() {
        return "UserRealm";
    }

    /**
     * 用户认证<br>
     * 先判断是否通过user角色进行登录，若不是便不认证。
     * 通过传入的token获取用户的username，并通过数据库查询获取该实例，最终进行认证。
     * @param token shiro的token
     * @return 如果token中的role不是user，不认证，返回null<br>
     *         如果是user，返回认证类
     * @throws AuthenticationException 抛出认证错误异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        if (!"user".equals(((UsernamePasswordRoleToken) token).getRole())) {
            return null;
        }
        String username = token.getPrincipal().toString();
        User user = userMapper.selectOneUserByUserName(username);
        return new SimpleAuthenticationInfo(user, user.getUserPassword(), getName());
    }

    /**
     * 用户授权<br>
     * 如果已认证的用户不是通过UserRealm进行认证的，则不授权。
     * 如果通过collection获取到的realmName是UserRealm，则授权角色user
     * @param collection 用于取出通过认证的名称
     * @return 将user角色授权给用户
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        if (!collection.getRealmNames().contains("UserRealm")) {
            return null;
        }
        collection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("user");
        return info;
    }
}
