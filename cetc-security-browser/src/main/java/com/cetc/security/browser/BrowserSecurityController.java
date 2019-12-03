package com.cetc.security.browser;

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
public class BrowserSecurityController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;
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
/*
    用户第三方登录时，发现应用userconnection中没有此用户信息，则会跳转到注册页面，跳转到注册页面之前会把用户信息存在session里
    注册页面会可以通过范围此服务使用providerSignInUtils来获取seession中的信息，并使用providerSignInUtils注册用户或者绑定用户。
 */
    @GetMapping("/social/user")
    public SocialUserInfo getSocualUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setHeadimg(connection.getImageUrl());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        return userInfo;
    }

    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse sessionInvalid(){
        String message = "session失效";
        return new SimpleResponse(message);
    }

}
