package com.ForMonk2.validators;

import com.ForMonk2.utils.Constants;
import com.ForMonk2.validators.annotations.ValidClientId;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidClientIdClass implements ConstraintValidator<ValidClientId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Constants.SOCIAL_CLIENTS.clientIds.contains(value);
    }
}
