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
import org.tc.dto.course.CourseDTO;
import org.tc.exceptions.CourseNotFoundException;
import org.tc.models.Category;
import org.tc.models.Course;
import org.tc.models.Evaluation;
import org.tc.models.User;
import org.tc.models.forms.CourseForm;
import org.tc.services.category.CategoryService;
import org.tc.services.course.CourseService;
import org.tc.services.evaluation.EvaluationService;
import org.tc.services.user.UserService;
import org.tc.services.usercourse.UserCourseService;
import org.tc.utils.converters.CourseConverter;
import org.tc.utils.converters.CourseDTOConverter;
import org.tc.utils.converters.CourseDetailsDTOConverter;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {
    private static final String HEADER_TITLE = "headerTitle";
    private static final String UPDATE_VIEW_NAME = "update";
    private static final String COURSES_OBJECT_NAME = "courses";
    private static final String ONE_COURSE_OBJECT_NAME = "course";
    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseConverter courseConverter;
    @Autowired
    private CourseDetailsDTOConverter courseDetailsDTOConverter;
    @Autowired
    private CourseDTOConverter courseDTOConverter;
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping(value = {"/courses"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("classpath:views/index");
        mav.addObject(HEADER_TITLE, "Courses");
        List<Course> course = courseService.getAll();
        List<CourseDTO> courses = courseDTOConverter
                .convertAll(courseService.getAll());
        mav.addObject(COURSES_OBJECT_NAME, courses);
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}"}, method = RequestMethod.GET)
    public ModelAndView details(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        ModelAndView mav = new ModelAndView("classpath:views/details");
        mav.addObject(HEADER_TITLE, "Course details");
        mav.addObject(ONE_COURSE_OBJECT_NAME, courseDetailsDTOConverter.convert(course));
        return mav;
    }

    @Secured("Lecturer")
    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("classpath:views/create");
        List<Category> categories = categoryService.getAll();
        mav.addObject(HEADER_TITLE, "Create course");
        mav.addObject("categories",categories);
        mav.addObject(ONE_COURSE_OBJECT_NAME, new CourseForm());
        return mav;
    }

    @Secured("Lecturer")
    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("course") CourseForm courseForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getAll();
            ModelAndView mav = new ModelAndView("classpath:views/create");
            mav.addObject(HEADER_TITLE, "Create course");
            mav.addObject("categories",categories);
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
            ModelAndView mav = new ModelAndView("classpath:views/"
                    + UPDATE_VIEW_NAME);
            mav.addObject(HEADER_TITLE, "Update course");
            mav.addObject(ONE_COURSE_OBJECT_NAME, course);
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
            ModelAndView mav = new ModelAndView("classpath:views/" + UPDATE_VIEW_NAME);
            mav.addObject(ONE_COURSE_OBJECT_NAME, course);
            mav.addObject(HEADER_TITLE, "Update course");
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }
        ModelAndView mav = new ModelAndView("classpath:views/" + UPDATE_VIEW_NAME);
        courseService.update(course);
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}/subscribe"}, method = RequestMethod.GET)
    public ModelAndView subscribe(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        ModelAndView mav = new ModelAndView("classpath:views/subscribe");
        mav.addObject(HEADER_TITLE, "Subscribe");
        mav.addObject(ONE_COURSE_OBJECT_NAME, course);
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
        boolean isCurrentUserSubscriber = userService.isSubscribed(course);
        if (isCurrentUserSubscriber) {
            ModelAndView mav = new ModelAndView("classpath:views/attend");
            mav.addObject(HEADER_TITLE, "Attend");
            mav.addObject(ONE_COURSE_OBJECT_NAME, course);
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
    public ModelAndView evaluate(@PathVariable("courseId") int id) {
        Course course = courseService.getById(id);
        User currentUser = userService.getCurrentUser();
        boolean isCurrentUserAttendee= userService.isAttendee(course);
        boolean isCurrentUserEvaluated = userService.isEvaluated(course);
        if (isCurrentUserAttendee &&
                !isCurrentUserEvaluated) {
            ModelAndView mav = new ModelAndView("classpath:views/evaluate");
            mav.addObject(HEADER_TITLE, "Evaluate");
            mav.addObject(ONE_COURSE_OBJECT_NAME, course);
            mav.addObject("evaluation", new Evaluation());
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
            mav.addObject(HEADER_TITLE, "Evaluate");
            mav.addObject(ONE_COURSE_OBJECT_NAME, course);
            mav.addObject("errors", results.getAllErrors());
            return mav;
        } else {
            evaluationService.evaluate(course, evaluation);
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
        mav.addObject(HEADER_TITLE, "Course Participants");
        mav.addObject(ONE_COURSE_OBJECT_NAME, course);
        mav.addObject("subscribers", courseService.getSubscribers(course));
        return mav;
    }
    @RequestMapping(value={"/mycourses"},method = RequestMethod.GET)
    public ModelAndView myCourses(){
        ModelAndView mav = new ModelAndView("classpath:views/mycourses");
        mav.addObject(HEADER_TITLE, "My courses");
        List<Course> myCourses = userService.getMyCourseList();
        mav.addObject(COURSES_OBJECT_NAME,courseDTOConverter.convertAll(myCourses));
        return mav;
    }
}
