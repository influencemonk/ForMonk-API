package com.ForMonk2.validators;

import com.ForMonk2.utils.Constants;
import com.ForMonk2.validators.annotations.ValidClientId;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.yaml.snakeyaml.scanner.Constant;


import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

public class ValidClientIdClass implements ConstraintValidator<ValidClientId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Constants.SOCIAL_CLIENTS.clientIds.contains(value);
    }
}
