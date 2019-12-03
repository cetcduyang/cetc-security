package com.cetc.security.app;

import com.cetc.security.app.openid.OpenIdCodeAuthenticationSecurityConfig;
import com.cetc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.cetc.security.core.properties.SecurityConstants;
import com.cetc.security.core.properties.SecurityProperties;
import com.cetc.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/*
AuthorizationServer config
 */
@Configuration
@EnableResourceServer
public class CetcResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    protected AuthenticationSuccessHandler cetcAuthenticationSuccessHandler;
    @Autowired
    protected AuthenticationFailureHandler cetcAuthenticationFailureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSocialSecurityConfigurer;

    @Autowired
    OpenIdCodeAuthenticationSecurityConfig openIdCodeAuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(cetcAuthenticationSuccessHandler)
                .failureHandler(cetcAuthenticationFailureHandler);
        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(springSocialSecurityConfigurer)
                .and()
                .apply(openIdCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                    .antMatchers(
                            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                            securityProperties.getBrowser().getLoginPage(),
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                            securityProperties.getBrowser().getSignUpUrl(),
                            securityProperties.getBrowser().getLogoutSuccessUrl(),
                            //                        securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
                            //                        securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html",
                            "/user/regist","/social/signUp")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .csrf().disable();
    }
}
