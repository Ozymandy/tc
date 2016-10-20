package org.tc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tc.models.User;
import org.tc.services.user.UserServiceInterface;

@Component
public class RegistrationValidator implements Validator {

    @Autowired
    private UserServiceInterface userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;
        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors,
                        "username", "Blank.UserName");
        if (userService.getByName(user.getUsername()) != null) {
            errors.rejectValue("username", "User.Exists");
        }
        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors,
                        "password", "Blank.Password");
        if (user.getPassword().length() < 6 ||
                user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.User.Password");
        }
    }
}
