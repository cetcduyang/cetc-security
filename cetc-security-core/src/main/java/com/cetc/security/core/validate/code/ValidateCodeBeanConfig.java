package com.cetc.security.core.validate.code;

import com.cetc.security.core.properties.SecurityProperties;
import com.cetc.security.core.validate.code.image.ImageCodeGenerator;
import com.cetc.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.cetc.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(ValidateCodeGenerator.class)
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return  codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return  new DefaultSmsCodeSender();
    }
}
