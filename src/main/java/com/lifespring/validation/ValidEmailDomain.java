package com.lifespring.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EmailDomainValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidEmailDomain {
    // Creating my custom annotation to be used


    String message() default "Invalid email domain"; // default error message
    Class<?>[] groups() default {}; // required for validation API
    Class<? extends Payload>[] payload() default {}; // required for validation API

    // Specify the allowed domain for email validation
    String allowedDomain() default "lifespring.com , gmail.com , yahoo.com , outlook.com"; // default allowed domain
}

