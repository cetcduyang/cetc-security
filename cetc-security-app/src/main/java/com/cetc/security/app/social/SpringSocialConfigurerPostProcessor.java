package com.cetc.security.app.social;

import com.cetc.security.core.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(StringUtils.equals(beanName,"springSocialSecurityConfigurer")){
            MySpringSocialConfigurer configurer = (MySpringSocialConfigurer) bean;
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
