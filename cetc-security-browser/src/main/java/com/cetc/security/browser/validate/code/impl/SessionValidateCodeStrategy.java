package com.cetc.security.browser.validate.code.impl;

import com.cetc.security.core.validate.code.ValidateCode;
import com.cetc.security.core.validate.code.ValidateCodeStrategy;
import com.cetc.security.core.validate.code.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

@Component
public class SessionValidateCodeStrategy implements ValidateCodeStrategy {
    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request,ValidateCodeType type) {
        return SESSION_KEY_PREFIX + type.toString().toUpperCase();
    }

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        sessionStrategy.setAttribute(request, getSessionKey(request,type), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
         return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, type));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        sessionStrategy.removeAttribute(request, getSessionKey(request, type));
    }
}
