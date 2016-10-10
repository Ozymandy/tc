package org.tc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login(Model model, String error) {
        ModelAndView mav = new ModelAndView("classpath:views/login");
        if (error != null) {
            mav.addObject("error","Invalid password");
        }
        return mav;
    }
}
