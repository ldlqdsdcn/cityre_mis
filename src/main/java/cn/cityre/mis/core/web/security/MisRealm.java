package cn.cityre.mis.core.web.security;

import cn.cityre.mis.util.Md5SaltTool;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 刘大磊 on 2017/8/22 10:54.
 */
public class MisRealm extends AuthorizingRealm {
    private static final Logger log= LoggerFactory.getLogger(MisRealm.class);
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
        log.debug("------------------------------>doGetAuthorizationInfo-11");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.addStringPermission("sys_menu");
        return authorizationInfo;
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
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String pwd = new String(usernamePasswordToken.getPassword());

        if (authenticationToken.getPrincipal().equals(account) && Md5SaltTool.validPassword(pwd, password)) {
            return authenticationInfo;
        }
        throw new AccountException("用户名或密码错误");

    }
}
