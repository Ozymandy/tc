package org.tc.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tc.models.User;
import org.tc.services.user.UserService;

public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userService.getByName(user.getUserName()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 3 ||
                user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
    }
}
