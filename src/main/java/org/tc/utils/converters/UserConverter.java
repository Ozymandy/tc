package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tc.models.User;
import org.tc.models.forms.RegistrationForm;
import org.tc.services.role.RoleService;

@Component
public class UserConverter implements Converter<RegistrationForm,User> {
    @Autowired
    private RoleService roleService;
    @Override
    public User convert(RegistrationForm source) {
        User user = new User();
        user.setPassword(source.getPassword());
        user.setEmail(source.getEmail());
        user.setRole(roleService.getByName(source.getRole()));
        user.setUsername(source.getUsername());
        return user;
    }
}
