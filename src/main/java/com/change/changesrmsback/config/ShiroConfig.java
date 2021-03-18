package com.change.changesrmsback.config;

import com.change.changesrmsback.shiro.AdminRealm;
import com.change.changesrmsback.shiro.UserRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Shiro配置类
 * @author Change
 */
@Configuration
public class ShiroConfig {

    /**
     * Shiro配置拦截
     * @param securityManager 用户认证安全管理
     * @return 拦截器工厂
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        // todo 拦截器先不配置
//        Map<String, String> map = new HashMap<>();
//        map.put("/login/logout", "roles[admin]");
//        bean.setFilterChainDefinitionMap(map);

        return bean;
    }

    /**
     * 配置用户认证安全管理
     * @param userRealm 用户认证域
     * @return 用户安全配置
     */
    @Bean
    public DefaultWebSecurityManager securityManager(
            @Qualifier("userRealm") UserRealm userRealm,
            @Qualifier("adminRealm") AdminRealm adminRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        Collection<Realm> collection = new ArrayList<>();
        collection.add(userRealm);
        collection.add(adminRealm);
        securityManager.setRealms(collection);
        return securityManager;
    }

    /**
     * 用户认证域的配置
     * @return 用户认证域
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 管理员认证域的配置
     * @return 管理员认证域
     */
    @Bean
    public AdminRealm adminRealm() {
        return new AdminRealm();
    }
}
