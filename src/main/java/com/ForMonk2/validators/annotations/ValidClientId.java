package com.ForMonk2.validators.annotations;

import com.ForMonk2.validators.ValidClientIdClass;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = ValidClientIdClass.class)
public @interface ValidClientId {
    String message() default "Please enter a valid clientId";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}