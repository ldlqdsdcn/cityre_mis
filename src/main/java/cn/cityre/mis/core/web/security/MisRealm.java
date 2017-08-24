package cn.cityre.mis.core.web.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by 刘大磊 on 2017/8/22 10:54.
 */
public class MisRealm extends AuthorizingRealm {
    @Value("${account.name}")
    private String account;
    @Value("${account.pwd}")
    private String password;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), "mis");
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)authenticationToken;
        String pwd=new String(usernamePasswordToken.getPassword());
        if (authenticationToken.getPrincipal().equals(account) && pwd.equals(password)) {
            return authenticationInfo;
        }
        throw new AccountException("用户名或密码错误");

    }
}
