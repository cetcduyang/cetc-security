package com.cetc.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
自定义注释
@Valid BindingResult
执行MyConstraintValidator.class的效验逻辑
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface  MyConstraint {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
