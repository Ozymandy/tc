package org.tc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
import org.tc.dto.CourseDTO;
import org.tc.exceptions.CourseNotFoundException;
import org.tc.models.Course;
import org.tc.models.Evaluation;
import org.tc.models.User;
import org.tc.models.forms.CourseForm;
import org.tc.models.usercourse.AttendeeCourse;
import org.tc.models.usercourse.SubscribersCourse;
import org.tc.services.course.CourseService;
import org.tc.services.evaluation.EvaluationService;
import org.tc.services.user.UserService;
import org.tc.services.usercourse.UserCourseService;
import org.tc.utils.UserCourseUtilit;
import org.tc.utils.converters.CourseConverter;
import org.tc.utils.converters.CourseDTOConverter;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseConverter courseConverter;
    @Autowired
    private CourseDTOConverter courseDTOConverter;
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private UserCourseUtilit userCourseUtilit;


    @RequestMapping(value = {"/courses"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("classpath:views/index");
        mav.addObject("headerH1", "Courses");
        List<Course> course = courseService.getAll();
        List<CourseDTO> courses = courseDTOConverter
                .convertAll(courseService.getAll());
        mav.addObject("courses", courses);
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}"}, method = RequestMethod.GET)
    public ModelAndView details(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        ModelAndView mav = new ModelAndView("classpath:views/details");
        mav.addObject("headerH1", "Course details");
        //may be the best way to send object instead of id
        mav.addObject("mark", userCourseUtilit.getAverageGrade(course));
        mav.addObject("course", course);
        return mav;
    }

    @Secured("Lecturer")
    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.GET)
    public ModelAndView create(@ModelAttribute("course")
                                       CourseForm courseForm) {
        ModelAndView mav = new ModelAndView("classpath:views/create");
        mav.addObject("headerH1", "Create course");
        return mav;
    }

    @Secured("Lecturer")
    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("course") CourseForm courseForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("classpath:views/create");
            mav.addObject("headerH1", "Create course");
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
        Course course = courseConverter.convert(courseForm);
        courseService.create(course);
        return new ModelAndView(new RedirectView("/courses"));
    }

    @RequestMapping(value = {"/courses/{id}/update"},
            method = RequestMethod.GET)
    public ModelAndView updateGet(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        if (courseService.isOwner(course)) {
            ModelAndView mav = new ModelAndView("classpath:views/update");
            mav.addObject("headerH1", "Update course");
            mav.addObject("course", course);
            return mav;
        } else {
            throw new CourseNotFoundException("Update course");
        }
    }

    @RequestMapping(value = {"/courses/{id}/update"},
            method = RequestMethod.POST)
    public ModelAndView updatePost(@PathVariable("id") int id,
                                   @Valid
                                   @ModelAttribute("course") Course course,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("classpath:views/update");
            mav.addObject("course", course);
            mav.addObject("headerH1", "Update course");
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
        ModelAndView mav = new ModelAndView("classpath:views/update");
        courseService.update(course);
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}/subscribe"}, method = RequestMethod.GET)
    public ModelAndView subscribe(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        ModelAndView mav = new ModelAndView("classpath:views/subscribe");
        mav.addObject("headerH1", "Subscribe");
        mav.addObject("course", course);
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}/subscribe"}, method = RequestMethod.POST)
    public ModelAndView subscribePost(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        userCourseService.subscribe(course);
        return new ModelAndView(new RedirectView("/courses"));
    }

    @RequestMapping(value = {"/courses/{id}/attend"}, method = RequestMethod.GET)
    public ModelAndView attend(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        if (userCourseUtilit.
                isSubscribed(userService.getCurrentUser(),course)) {
            ModelAndView mav = new ModelAndView("classpath:views/attend");
            mav.addObject("headerH1", "Attend");
            mav.addObject("course", course);
            return mav;
        } else {
            return new ModelAndView(new RedirectView("/403"));
        }
    }

    @RequestMapping(value = {"/courses/{id}/attend"},
            method = RequestMethod.POST)
    public ModelAndView attendPost(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        userCourseService.attend(course);
        return new ModelAndView(new RedirectView("/courses"));
    }

    @RequestMapping(value = {"/courses/{courseId}/evaluate"},
            method = RequestMethod.GET)
    public ModelAndView evaluate(@PathVariable("courseId") int id,
                                 @ModelAttribute("evaluation") Evaluation evaluation) {
        Course course = courseService.getById(id);
        User currentUser = userService.getCurrentUser();
        if (userCourseUtilit.isAttendee(currentUser,course) &&
                !userCourseUtilit.isEvaluated(currentUser,course)) {
            ModelAndView mav = new ModelAndView("classpath:views/evaluate");
            mav.addObject("headerH1", "Evaluate");
            mav.addObject("course", course);
            return mav;
        } else {
            return new ModelAndView(new RedirectView("/403"));
        }
    }

    @RequestMapping(value = {"/courses/{courseId}/evaluate"},
            method = RequestMethod.POST)
    public ModelAndView evaluate(@PathVariable("courseId") int id,
                                 @Valid @ModelAttribute("evaluation") Evaluation evaluation,
                                 BindingResult results) {
        Course course = courseService.getById(id);
        if (results.hasErrors()) {
            ModelAndView mav = new ModelAndView("classpath:views/evaluate");
            mav.addObject("headerH1", "Evaluate");
            mav.addObject("course", course);
            mav.addObject("errors", results.getAllErrors());
            return mav;
        } else {
            evaluationService.evaluate(course,evaluation);
            return new ModelAndView(new RedirectView("/courses"));
        }
    }

    @RequestMapping(value = {"/courses/{courseId}/participants"},
            method = RequestMethod.GET)
    public ModelAndView participants(@PathVariable("courseId") int id) {
        Course course = courseService.getById(id);
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        ModelAndView mav = new ModelAndView("classpath:views/participants");
        mav.addObject("headerH1", "Course Participants");
        mav.addObject("course", course);
        mav.addObject("subscribers", userCourseUtilit.getSubscribers(course));
        return mav;
    }
}
