package com.cetc.security.browser.logout;

import com.cetc.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CetcLogoutSuccessHandler implements LogoutSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String logoutSuccessUrl;
    @Autowired
    private ObjectMapper objectMapper;

    public CetcLogoutSuccessHandler() {
    }

    public CetcLogoutSuccessHandler(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("CetcLogoutSuccessHandler logout success!  logoutSuccessUrl:"+logoutSuccessUrl);
        if(StringUtils.isBlank(logoutSuccessUrl)){
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("logout success!")));
        }else{
            httpServletResponse.sendRedirect(logoutSuccessUrl);
        }
    }
}
