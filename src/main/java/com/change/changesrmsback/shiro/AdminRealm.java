package com.change.changesrmsback.shiro;

import com.change.changesrmsback.entity.Admin;
import com.change.changesrmsback.mapper.AdminMapper;
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
 * 管理员认证域
 * @author Change
 */
public class AdminRealm extends AuthorizingRealm {

    private AdminMapper adminMapper;

    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /**
     * 在创建认证信息的时候用到，并且在授权时也会获取该name
     * @return 返回"UserRealm"
     */
    @Override
    public String getName() {
        return "AdminRealm";
    }

    /**
     * 管理员认证<br>
     * 先判断是否通过admin角色进行登录，若不是便不认证。
     * 通过传入的token获取用户的username，并通过数据库查询获取该实例，最终进行认证。
     * @param token shiro的token
     * @return 如果token中的role不是admin，不认证，返回null<br>
     *         如果是admin，返回认证类
     * @throws AuthenticationException 抛出认证错误异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("======管理员认证======before");
        if (!"admin".equals(((UsernamePasswordRoleToken) token).getRole())) {
            return null;
        }
        System.out.println("======管理员认证======");
        String username = token.getPrincipal().toString();
        Admin admin = adminMapper.selectOneAdminByAdminName(username);
        return new SimpleAuthenticationInfo(admin, admin.getAdminPassword(), getName());
    }

    /**
     * 管理员授权<br>
     * 如果已认证的用户不是通过UserRealm进行认证的，则不授权。
     * 如果通过collection获取到的realmName是UserRealm，则授权角色。
     * 角色取决于该用户的role属性。
     * @param collection 用于取出通过认证的名称，以及已经认证的实体
     * @return 将对应角色授权给用户
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        System.out.println("======管理员授权======before");
        if (!collection.getRealmNames().contains("AdminRealm")) {
            return null;
        }
        System.out.println("======管理员授权======");
        Admin admin = (Admin) collection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(admin.getRole());
        return info;
    }
}
