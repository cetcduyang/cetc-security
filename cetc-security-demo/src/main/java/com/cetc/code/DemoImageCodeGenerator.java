package com.cetc.code;

import com.cetc.security.core.validate.code.ValidateCode;
import com.cetc.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("ImageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        System.out.println("自己重写图形验证码生成代码");
        return null;
    }
}
