package org.tc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tc.models.Course;
import org.tc.services.course.CourseServiceInterface;

@Controller
public class CourseController {
    @Autowired
    private CourseServiceInterface courseService;

    @RequestMapping(value = {"/courses"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("classpath:views/index");
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        String username = auth.getName();
        mav.addObject("courses", courseService.getAll());
        mav.addObject("username", username);
        return mav;
    }

    //TODO Notfound id 404 page
    @RequestMapping(value = {"/courses/{id}"}, method = RequestMethod.GET)
    public ModelAndView details(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("classpath:views/details");
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        String username = auth.getName();
        mav.addObject("username", username);
        Course course = courseService.getById(id);
        mav.addObject("course", course);
        return mav;
    }
}
