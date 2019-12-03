package com.cetc.security.app.authentication;


import com.cetc.security.core.properties.LoginType;
import com.cetc.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class CetcAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("login success!");

        String header = request.getHeader("Authorization");
        if (header != null && header.toLowerCase().startsWith("basic ")) {
            try {
                String[] tokens = this.extractAndDecodeHeader(header, request);
                assert tokens.length == 2;
                String clientId = tokens[0];
                String clientSecret = tokens[1];
                ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
                if(clientDetails == null){
                    throw new UnapprovedClientAuthenticationException("loadClientByClientId clientDetails is null:" + clientId);
                }else if (!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
                    throw new UnapprovedClientAuthenticationException("request clientSecret is not equals of clientDetails clientSecret");
                }
                TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
                OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
                OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
                OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(oAuth2AccessToken));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
                throw new UnapprovedClientAuthenticationException("请求头中没有basic client信息");
            }




    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
