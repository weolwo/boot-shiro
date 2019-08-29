package com.poplar.realm;

import com.poplar.po.Role;
import com.poplar.po.User;
import com.poplar.service.UserService;
import com.poplar.po.Permission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权");

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 角色与权限字符串集合
            Collection<String> rolesCollection = new HashSet<>();
            Collection<String> premissionCollection = new HashSet<>();

            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                rolesCollection.add(role.getName());
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    premissionCollection.add(permission.getUrl());
                }
                info.addStringPermissions(premissionCollection);
            }
            info.addRoles(rolesCollection);
            return info;
        }
        return null;
    }

    /**
     * 认证
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("执行认证");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User bean = userService.findByName(token.getUsername());

        if (bean == null) {
            throw new UnknownAccountException();
        }
        if (bean.getPassWordSalt() != null) {

            ByteSource credentialsSalt = ByteSource.Util.bytes(bean.getPassWordSalt());
            return new SimpleAuthenticationInfo(bean, bean.getPassWord(),
                    credentialsSalt, getName());
        }
        return null;
    }

    public static void main(String[] args) {
        String hashAlgorithName = "MD5";
        String password = "123456";
        //加密迭代次数
        int hashIterations = 2;
        ByteSource credentialsSalt = ByteSource.Util.bytes("pop");
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        System.out.println(obj);
    }
}
