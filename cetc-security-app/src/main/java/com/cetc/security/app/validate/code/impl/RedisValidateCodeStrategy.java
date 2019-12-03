package com.cetc.security.app.validate.code.impl;

import com.cetc.security.core.validate.code.ValidateCode;
import com.cetc.security.core.validate.code.ValidateCodeException;
import com.cetc.security.core.validate.code.ValidateCodeStrategy;
import com.cetc.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

@Component
public class RedisValidateCodeStrategy implements ValidateCodeStrategy {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request,type),code,5, TimeUnit.MINUTES);
    }

    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("请求头中没有deviceId");
        }
        return "code:"+type.toString().toUpperCase()+":"+deviceId;
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request,type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request,type));
    }
}
