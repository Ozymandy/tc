package org.tc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.tc.models.Role;
import org.tc.models.User;
import org.tc.models.forms.RegistrationForm;
import org.tc.utils.converters.UserConverter;
import org.tc.validators.RegistrationValidator;
import org.tc.security.SecurityService;
import org.tc.security.filterUtils.AuthValidationException;
import org.tc.services.role.RoleService;
import org.tc.services.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RegistrationValidator userValidator;
    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login(@ModelAttribute("user") User user,
                              HttpServletRequest req) {
        ModelAndView mav = new ModelAndView("classpath:views/login");
        try {
            AuthValidationException validationException =
                    (AuthValidationException) req.getSession()
                            .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (validationException != null) {
                mav.addObject("errors", validationException.getErrors());
            }
        } catch (ClassCastException e) {
            List<String> errors = new ArrayList();
            errors.add("Invalid.Username.Or.Login");
            mav.addObject("errors", errors);
        }
        return mav;

    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(@ModelAttribute("newUser") RegistrationForm form) {
        List<Role> roles = roleService.getAll();
        ModelAndView mav = new ModelAndView("classpath:views/registration");
        mav.addObject("roles", roles);
        return mav;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration
            (@ModelAttribute("newUser") RegistrationForm form,
             BindingResult bindingResult) {
        userValidator.validate(form, bindingResult);
        ModelAndView mav = new ModelAndView("classpath:views/registration");
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAll();
            mav.addObject("roles", roles);
            mav.addObject("errors",bindingResult.getAllErrors());
            return mav;
        }
        User newUser = userConverter.convert(form);
        userService.create(newUser);
        securityService.autologin(form.getUsername(), form.getPassword());
        return new ModelAndView(new RedirectView("/courses"));
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutGet(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView("classpath:views/logout");
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        String username = auth.getName();
        String back = req.getHeader("referer");
        mav.addObject("back", back);
        mav.addObject("username", username);
        return mav;
    }
}
