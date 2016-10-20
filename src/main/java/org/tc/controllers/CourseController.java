package org.tc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.tc.exceptions.CourseNotFoundException;
import org.tc.models.Course;
import org.tc.services.course.CourseServiceInterface;
import org.tc.validators.CourseValidator;

@Controller
public class CourseController {
    @Autowired
    private CourseValidator validator;
    @Autowired
    private CourseServiceInterface courseService;

    @RequestMapping(value = {"/courses"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("classpath:views/index");
        mav.addObject("courses", courseService.getAll());
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}"}, method = RequestMethod.GET)
    public ModelAndView details(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        if (course != null) {
            ModelAndView mav = new ModelAndView("classpath:views/details");
            mav.addObject("course", course);
            return mav;
        } else {
            throw new CourseNotFoundException("Update course");
        }
    }

    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("classpath:views/create");
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        String username = auth.getName();
        mav.addObject("username", username);
        return mav;
    }

    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("course") Course course,
                                   BindingResult bindingResult) {
        validator.validate(course, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("classpath:views/create");
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
        courseService.create(course);
        return new ModelAndView(new RedirectView("/"));
    }
    //TODO test get without mav
    //TODO unknown
    @RequestMapping(value = {"/courses/{id}/update"}, method = RequestMethod.GET)
    public ModelAndView updateGet(@PathVariable("id") int id){
        Course course = courseService.getById(id);
        if(course!=null) {
            ModelAndView mav = new ModelAndView("classpath:views/update");
            mav.addObject("course", course);
            return mav;
        }
        else{
            throw new CourseNotFoundException("Update course");
        }
    }
    @RequestMapping(value = {"/courses/{id}/update"}, method = RequestMethod.POST)
    public ModelAndView updatePost(@PathVariable("id") int id,
                                   @ModelAttribute("course") Course course){
        ModelAndView mav = new ModelAndView("classpath:views/update");
        courseService.update(course);
        return mav;
    }
}
