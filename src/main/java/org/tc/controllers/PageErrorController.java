package org.tc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageErrorController {

    @RequestMapping(value = "/unknown")
    public ModelAndView unknownCourse(RuntimeException e) {
        ModelAndView mav = new ModelAndView("classpath:views/unknown");
        return mav;
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView handle404() {
        ModelAndView mav = new ModelAndView("classpath:views/404");
        return mav;
    }
}
