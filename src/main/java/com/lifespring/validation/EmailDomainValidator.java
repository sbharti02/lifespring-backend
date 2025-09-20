package com.lifespring.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailDomainValidator implements ConstraintValidator<ValidEmailDomain,String> {

    private String allowedDomains;
    public void initialize(ValidEmailDomain constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.allowedDomains = constraintAnnotation.allowedDomain();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {


            if(value ==null || value.isBlank()){
                return false;
            }

        String[] domains = allowedDomains.split(",");

        for(String allowed : domains){
            if(value.toLowerCase().endsWith("@"+allowed.trim())){
                return true;
            }
        }


        return false;
    }

}
