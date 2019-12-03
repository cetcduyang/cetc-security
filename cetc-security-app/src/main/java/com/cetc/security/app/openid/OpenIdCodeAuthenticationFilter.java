package com.cetc.security.app.openid;

import com.cetc.security.core.authentication.mobile.SmsCodeAuthenticationToken;
import com.cetc.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenIdCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPEIN;
    private String providerIdParameter =  SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
    private boolean postOnly = true;

    public OpenIdCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID, "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       System.out.println("OpenIdCodeAuthenticationFilter--");
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String openId = this.obtainValue(request,this.openIdParameter);
            String providerId = this.obtainValue(request,this.providerIdParameter);
            if (openId == null) {
                openId = "";
            }
            if (providerId == null) {
                providerId = "";
            }
            openId = openId.trim();
            providerId = providerId.trim();
            OpenIdCodeAuthenticationToken authRequest = new OpenIdCodeAuthenticationToken(openId,providerId);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainValue(HttpServletRequest request,String Parameter) {
        return request.getParameter(Parameter);
    }

    protected void setDetails(HttpServletRequest request, OpenIdCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getOpenIdParameter() {
        return openIdParameter;
    }

    public void setOpenIdParameter(String openIdParameter) {
        this.openIdParameter = openIdParameter;
    }

    public String getProviderIdParameter() {
        return providerIdParameter;
    }

    public void setProviderIdParameter(String providerIdParameter) {
        this.providerIdParameter = providerIdParameter;
    }

}

