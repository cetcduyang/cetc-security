package com.cetc.security.core.authentication;

import com.cetc.security.core.properties.SecurityConstants;
import com.cetc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CetcAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry interceptUrlRegistry) {
        interceptUrlRegistry.antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                securityProperties.getBrowser().getLoginPage(),
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                securityProperties.getBrowser().getSignUpUrl(),
                securityProperties.getBrowser().getLogoutSuccessUrl(),
                "/user/regist","/session/invalid")
                .permitAll();

        interceptUrlRegistry.anyRequest().access("@rbacService.hasPermission(request,authentication)");
    }
}
