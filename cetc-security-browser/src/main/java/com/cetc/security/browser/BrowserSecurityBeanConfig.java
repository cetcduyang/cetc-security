/**
 * 
 */
package com.cetc.security.browser;

import com.cetc.security.browser.logout.CetcLogoutSuccessHandler;
import com.cetc.security.browser.session.CetcExpiredSessionStrategy;
import com.cetc.security.browser.session.CetcInvalidSessionStrategy;
import com.cetc.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;


/**
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new CetcInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}
	
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		String invalidUrl = securityProperties.getBrowser().getSession().getSessionInvalidUrl();
		logger.info("invalidUrl:"+invalidUrl);
		SessionInformationExpiredStrategy sessionInformationExpiredStrategy = new CetcExpiredSessionStrategy(invalidUrl);
		logger.info(sessionInformationExpiredStrategy.toString());
		return sessionInformationExpiredStrategy;//new CetcExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}

	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public CetcLogoutSuccessHandler cetcLogoutSuccessHandler(){
		CetcLogoutSuccessHandler cetcLogoutSuccessHandler =
				new CetcLogoutSuccessHandler(securityProperties.getBrowser().getLogoutSuccessUrl());
		return cetcLogoutSuccessHandler;
	}
	
}
