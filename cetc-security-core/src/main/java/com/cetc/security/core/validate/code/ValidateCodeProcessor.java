package com.cetc.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/*
   最顶层接口
 */

public interface ValidateCodeProcessor {

    /**
     * 创建校验码
     *校验码-创建和检查
     * 创建create分为3步骤由AbstractValidateCodeProcessor抽象实现：
     *     1、generate创建验证码 （ValidateCodeGenerator接口的实现创建验证码）
     *     2、存储ValidateCode信息到session
     *     3、send抽象方法（验证码发送到响应或者发短信等）
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;


    /**
     * 校验验证码
     *
     * 用户使用create方法第3步返回的验证码登录系统，在过滤器链最前面添加过滤器
     * ValidateCodeFilter
     * 效验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);

}
