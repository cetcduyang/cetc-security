package com.cetc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint,String> {


    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("my MyConstraintValidator initialize");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println(value);
        return true;
    }
}
