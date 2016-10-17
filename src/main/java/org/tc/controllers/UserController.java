package org.tc.controllers;


import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tc.models.User;
import org.tc.security.filterUtils.AuthValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login(@ModelAttribute("user") User user,
                              HttpServletRequest req, String error) {
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

}
