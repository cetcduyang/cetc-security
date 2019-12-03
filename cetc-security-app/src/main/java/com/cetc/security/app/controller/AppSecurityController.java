package com.cetc.security.app.controller;

import com.cetc.security.app.social.AppSignInUtils;
import com.cetc.security.core.properties.SecurityProperties;
import com.cetc.security.core.support.SimpleResponse;
import com.cetc.security.core.support.SocialUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AppSecurityController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();
    @Autowired
    private SecurityProperties securityProperties;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Autowired
    ProviderSignInUtils providerSignInUtils;
    @Autowired
    AppSignInUtils appSignInUtils;

    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocualUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setHeadimg(connection.getImageUrl());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        appSignInUtils.saveConnectionData(new ServletWebRequest(request),connection.createData());
        return userInfo;
    }


    /*
当需要身份认证时跳转到此controller
 */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAutentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest !=null){
            String target = savedRequest.getRedirectUrl();
            logger.info("请求url："+target);
            logger.info("配置的重定向登录地址："+securityProperties.getBrowser().getLoginPage());
//            if(StringUtils.endsWithIgnoreCase(target,".html")){
            redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
//            }
        }

        return new SimpleResponse("访问的服务需要身份认证！");
    }

}
