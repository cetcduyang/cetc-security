package com.cetc.security.app.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class OpenIdCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private AuthenticationFailureHandler cetcAuthenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler cetcAuthenticationSuccessHandler;
    @Autowired
    private SocialUserDetailsService userDetailsService;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception{
        OpenIdCodeAuthenticationFilter openIdCodeAuthenticationFilter = new OpenIdCodeAuthenticationFilter();
        openIdCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        openIdCodeAuthenticationFilter.setAuthenticationSuccessHandler(cetcAuthenticationSuccessHandler);
        openIdCodeAuthenticationFilter.setAuthenticationFailureHandler(cetcAuthenticationFailureHandler);

        OpenIdCodeAuthenticationProvider openIdCodeAuthenticationProvider = new OpenIdCodeAuthenticationProvider();
        openIdCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        openIdCodeAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        http.authenticationProvider(openIdCodeAuthenticationProvider)
                .addFilterAfter(openIdCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }


}
