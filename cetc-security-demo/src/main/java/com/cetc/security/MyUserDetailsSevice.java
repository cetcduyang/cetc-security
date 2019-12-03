package com.cetc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsSevice implements UserDetailsService, SocialUserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("正在表单登陆的用户："+s);
        //这里通过用户名去数据库查找用户信息及权限信息，返回User对象。
        // 框架通过比对用户表单输入的用户名和密码及配置中对权限的要求，来验证用户是否登陆成功
        //AuthorityUtils.commaSeparatedStringToAuthorityList通过","转换成GrantedAuthority集合
        //return new User(s,"123456",AuthorityUtils.commaSeparatedStringToAuthorityList("admin,member"));
        //中间的4个boolean值根据具体业务信息判断是否可用、是否过期、密码是否过期、是否锁定：
        return buildUserDetails(s);
    }

    /*
    社交登录时使用此UserDetails
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("正在通过社交网站登陆的用户："+userId);
        return buildUserDetails(userId);
    }

    private SocialUserDetails buildUserDetails(String userId) {
        return new SocialUser(userId,passwordEncoder.encode("123456"),  //加密动作应该在注册的时候使用
               true,true,true,true,
               AuthorityUtils.commaSeparatedStringToAuthorityList("admin,member,ROLE_USER,ROLE_ADMIN"));
    }
}
