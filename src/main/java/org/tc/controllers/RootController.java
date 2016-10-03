package org.tc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tc.services.course.CourseServiceInterface;

@Controller
public class RootController {
    @Autowired
    private CourseServiceInterface courseService;
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("views/index");
        mav.addObject("courses",courseService.getAll());
        return mav;
    }
}
