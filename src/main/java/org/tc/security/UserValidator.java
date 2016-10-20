package org.tc.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tc.models.User;
import org.tc.security.Forms.UserForm;

@Component
public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm user = (UserForm) o;
        System.out.println("keeeek");
        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    }
}
