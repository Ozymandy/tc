package org.tc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageErrorController {
    private static final String HEADER_TITLE = "headerTitle";
    private static final String NOT_FOUND_VIEW_NAME = "404";
    private static final String ACCESS_DENIED_VIEW_NAME = "403";
    private static final String UNKNOWN_COURSE_VIEW_NAME = "unknown";

    @RequestMapping(value = "/unknown")
    public ModelAndView unknownCourse(RuntimeException e) {
        ModelAndView mav = new ModelAndView(UNKNOWN_COURSE_VIEW_NAME);
        mav.addObject(HEADER_TITLE, "Unknown course");
        return mav;
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView handle404() {
        ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW_NAME);
        return mav;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest req) {
        //TODO more custom page
        ModelAndView mav = new ModelAndView(ACCESS_DENIED_VIEW_NAME);
        mav.addObject(HEADER_TITLE, "Access denied");
        return mav;
    }
}
