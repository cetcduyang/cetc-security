package com.cetc.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

import javax.annotation.Resource;

/*
AuthorizationServer config
 */
@Configuration
@EnableAuthorizationServer
public class CetcAuthorizationServerConfig  {
    /* extends AuthorizationServerConfigurerAdapter
        如果继承了此类做配置是必须制定AuthenticationManager和UserDetailsService
        如果没继承会去容器里找并自动配置
     */

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    ClientDetailsService clientDetailsService;
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService)
//        .setClientDetailsService(clientDetailsService);
//    }
//
//    /*
//    当复写了这个方法后，配置文件中的
//    security:
//  oauth2:
//    client:
//      client-id: cetc_pipi
//      client-secret: cetc_secret 就不生效了
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("cetc_pipi").secret("cetc_secret")
//                .accessTokenValiditySeconds(7200)  //令牌有效时间
//                .authorizedGrantTypes("refresh_token","password","authorization_code")  //授权模式
//                .scopes("all","admin","read","write");
//    }


}
