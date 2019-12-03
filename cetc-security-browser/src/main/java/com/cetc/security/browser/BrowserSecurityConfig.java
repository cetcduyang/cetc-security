package com.cetc.security.browser;

import com.cetc.security.browser.logout.CetcLogoutSuccessHandler;
import com.cetc.security.core.authentication.AuthorizeConfigManager;
import com.cetc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.cetc.security.core.properties.SecurityConstants;
import com.cetc.security.core.properties.SecurityProperties;
import com.cetc.security.core.validate.code.ValidateCodeSecurityConfig;
import com.cetc.security.browser.session.CetcExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import com.cetc.security.core.authentication.web.AbstractWebSecurityConfig;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends AbstractWebSecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

//    @Qualifier("myUserDetailsSevice")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    //SocialAuthenticationFilter
    @Autowired
    private SpringSocialConfigurer springSocialSecurityConfigurer;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(springSocialSecurityConfigurer)
                .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRemeberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .and()
                .sessionManagement()
                    .invalidSessionStrategy(invalidSessionStrategy)
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    //是指达到最大登录数时阻止登录行为
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                    .and()
                .logout()
//                    .logoutUrl("/SignOut")  //修改登出服务的地址,不配置就是/logout
//                    .logoutSuccessUrl("/logout.html")   //退出成功以后的处理
                    .logoutSuccessHandler(logoutSuccessHandler)
//                    .deleteCookies("删除指定名字的cookie")
                    .deleteCookies("JSESSIONID")
                    .and()
//                .authorizeRequests()
//                    .antMatchers(
//                            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
//                            securityProperties.getBrowser().getLoginPage(),
//                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
//                            securityProperties.getBrowser().getSignUpUrl(),
//                            securityProperties.getBrowser().getLogoutSuccessUrl(),
//                            "/user/regist","/session/invalid")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
                .csrf().disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}