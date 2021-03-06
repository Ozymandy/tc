package org.tc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.tc.dto.course.CourseDTO;
import org.tc.mail.MailNotificationSender;
import org.tc.models.Category;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.models.Evaluation;
import org.tc.models.forms.CourseForm;
import org.tc.models.forms.CourseUpdateForm;
import org.tc.models.forms.DecisionForm;
import org.tc.services.category.CategoryService;
import org.tc.services.course.CourseService;
import org.tc.services.decision.DecisionService;
import org.tc.services.evaluation.EvaluationService;
import org.tc.services.role.RoleService;
import org.tc.services.user.UserService;
import org.tc.services.usercourse.UserCourseService;
import org.tc.utils.converters.CourseApproveDTOConverter;
import org.tc.utils.converters.CourseConverter;
import org.tc.utils.converters.CourseDTOConverter;
import org.tc.utils.converters.CourseDetailsDTOConverter;
import org.tc.utils.converters.CourseUpdateConverter;
import org.tc.utils.converters.DecisionConverter;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {
    private static final String HEADER_TITLE = "headerTitle";
    private static final String UPDATE_VIEW = "update";
    private static final String COURSES_VIEW = "courses";
    private static final String DETAILS_VIEW = "details";
    private static final String CREATE_VIEW = "create";
    private static final String SUBSCRIBE_VIEW = "subscribe";
    private static final String ATTEND_VIEW = "attend";
    private static final String EVALUATE_VIEW = "evaluate";
    private static final String PARTICIPANTS_VIEW = "participants";
    private static final String COURSES_KEY = "courses";
    private static final String SINGLE_COURSE_KEY = "course";
    private static final String CATEGORIES_KEY = "categories";
    private static final String ERRORS_KEY = "errors";
    private static final String EVALUATION_KEY = "evaluation";
    private static final String APPROVE_VIEW = "approve";
    private static final String DELETE_COURSE_VIEW = "delete";
    private static final String START_COURSE_VIEW = "start";
    private static final String FINISH_COURSE_VIEW = "finish";
    private static final String NOTIFY_COURSE_VIEW = "notify";
    private static final String ACCESS_DENIED_PAGE = "/403";
    private static final String COURSES_PAGE = "/courses";
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseUpdateConverter courseUpdateConverter;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DecisionConverter decisionConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseConverter courseConverter;

    @Autowired
    private CourseDetailsDTOConverter courseDetailsDTOConverter;

    @Autowired
    private CourseApproveDTOConverter courseApproveDTOConverter;

    @Autowired
    private CourseDTOConverter courseDTOConverter;

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private DecisionService decisionService;
    @Autowired
    private MailNotificationSender mailSender;

    @RequestMapping(value = {"/courses"}, method = RequestMethod.GET)
    public ModelAndView index(@ModelAttribute("category") Category category) {
        ModelAndView mav = new ModelAndView(COURSES_VIEW);
        mav.addObject(HEADER_TITLE, "Courses");
        List<Course> course;
        if (category.getCategoryName() == null ||
                category.getCategoryName().equals("All")) {
            course = courseService.getAll();
        } else {
            category = categoryService.getCategoryByName(category.getCategoryName());
            course = category.getCourses();
        }
        mav.addObject(CATEGORIES_KEY, categoryService.getAll());
        List<CourseDTO> courses = courseDTOConverter
                .convertAll(course);
        mav.addObject(COURSES_KEY, courses);
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}"}, method = RequestMethod.GET)
    public ModelAndView details(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canViewCourse = courseService.canViewCourse(course);
        if (canViewCourse) {
            ModelAndView mav = new ModelAndView(DETAILS_VIEW);
            mav.addObject(HEADER_TITLE, "Course details");
            mav.addObject(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @Secured("Lecturer")
    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView(CREATE_VIEW);
        List<Category> categories = categoryService.getAll();
        mav.addObject(HEADER_TITLE, "Create course");
        mav.addObject(CATEGORIES_KEY, categories);
        mav.addObject(SINGLE_COURSE_KEY, new CourseForm());
        return mav;
    }

    @Secured("Lecturer")
    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("course") CourseForm courseForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getAll();
            ModelAndView mav = new ModelAndView(CREATE_VIEW);
            mav.addObject(HEADER_TITLE, "Create course");
            mav.addObject(CATEGORIES_KEY, categories);
            mav.addObject(ERRORS_KEY, bindingResult.getAllErrors());
            return mav;
        }
        Course course = courseConverter.convertToCourse(courseForm);
        courseService.create(course);
        return new ModelAndView(new RedirectView(COURSES_PAGE));
    }

    @RequestMapping(value = {"/courses/{id}/update"},
            method = RequestMethod.GET)
    public ModelAndView updateGet(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canUpdate = courseService.canUpdate(course);
        if (canUpdate) {
            ModelAndView mav = new ModelAndView(UPDATE_VIEW);
            mav.addObject(HEADER_TITLE, "Update course");
            List<Category> categories = categoryService.getAll();
            mav.addObject(CATEGORIES_KEY, categories);
            mav.addObject(SINGLE_COURSE_KEY, courseUpdateConverter
                    .convertToUpdateForm(course));
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = {"/courses/{id}/update"},
            method = RequestMethod.POST)
    public ModelAndView updatePost(@PathVariable("id") int id,
                                   @Valid
                                   @ModelAttribute("course") CourseUpdateForm course,
                                   BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView(UPDATE_VIEW);
        List<Category> categories = categoryService.getAll();
        mav.addObject(CATEGORIES_KEY, categories);
        mav.addObject(SINGLE_COURSE_KEY, course);
        mav.addObject(HEADER_TITLE, "Update course");
        if (bindingResult.hasErrors()) {
            mav.addObject(ERRORS_KEY, bindingResult.getAllErrors());
            return mav;
        }
        course.setCourseId(id);
        courseService.update(courseUpdateConverter.convertToCourse(course));
        return mav;
    }

    @RequestMapping(value = {"/courses/{id}/subscribe"}, method = RequestMethod.GET)
    public ModelAndView subscribe(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canSubscribe = courseService.canSubscribe(course);
        if (canSubscribe) {
            ModelAndView mav = new ModelAndView(SUBSCRIBE_VIEW);
            mav.addObject(HEADER_TITLE, "Subscribe");
            mav.addObject(SINGLE_COURSE_KEY, course);
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = {"/courses/{id}/subscribe"}, method = RequestMethod.POST)
    public ModelAndView subscribePost(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        userCourseService.subscribe(course);
        return new ModelAndView(new RedirectView(COURSES_PAGE));
    }

    @RequestMapping(value = {"/courses/{id}/attend"}, method = RequestMethod.GET)
    public ModelAndView attend(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canAttend = courseService.canAttend(course);
        if (canAttend) {
            ModelAndView mav = new ModelAndView(ATTEND_VIEW);
            mav.addObject(HEADER_TITLE, "Attend");
            mav.addObject(SINGLE_COURSE_KEY, course);
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
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
        boolean canEvaluate = courseService.canEvaluate(course);
        if (canEvaluate) {
            ModelAndView mav = new ModelAndView(EVALUATE_VIEW);
            mav.addObject(HEADER_TITLE, "Evaluate");
            mav.addObject(SINGLE_COURSE_KEY, course);
            mav.addObject(EVALUATION_KEY, new Evaluation());
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = {"/courses/{courseId}/evaluate"},
            method = RequestMethod.POST)
    public ModelAndView evaluate(@PathVariable("courseId") int id,
                                 @Valid @ModelAttribute("evaluation") Evaluation evaluation,
                                 BindingResult results) {
        Course course = courseService.getById(id);
        if (results.hasErrors()) {
            ModelAndView mav = new ModelAndView(EVALUATE_VIEW);
            mav.addObject(HEADER_TITLE, "Evaluate");
            mav.addObject(SINGLE_COURSE_KEY, course);
            mav.addObject(ERRORS_KEY, results.getAllErrors());
            return mav;
        } else {
            evaluationService.evaluate(course, evaluation);
            return new ModelAndView(new RedirectView(COURSES_PAGE));
        }
    }

    @RequestMapping(value = {"/courses/{courseId}/participants"},
            method = RequestMethod.GET)
    public ModelAndView participants(@PathVariable("courseId") int id) {
        Course course = courseService.getById(id);
        boolean canViewCourse = courseService.canViewCourse(course);
        if (canViewCourse) {
            ModelAndView mav = new ModelAndView(PARTICIPANTS_VIEW);
            mav.addObject(HEADER_TITLE, "Course Participants");
            mav.addObject(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = {"/mycourses"}, method = RequestMethod.GET)
    public ModelAndView myCourses(@ModelAttribute("category") Category category) {
        ModelAndView mav = new ModelAndView(COURSES_VIEW);
        mav.addObject(HEADER_TITLE, "My courses");
        List<Course> myCourses = userService.getMyCourseList();
        if (category.getCategoryName() != null &&
                !category.getCategoryName().equals("All")) {
            userService.getMyCourseListByCategory(category);
        }
        mav.addObject(CATEGORIES_KEY, categoryService.getAll());
        mav.addObject(COURSES_KEY, courseDTOConverter.convertAll(myCourses));
        return mav;
    }

    @RequestMapping(value = "/send_to_review/{id}", method = RequestMethod.GET)
    public ModelAndView sendToReview(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canSendToReview = courseService.canSendToReview(course);
        if (canSendToReview) {
            courseService.makeProposal(course);
            return new ModelAndView(new RedirectView(COURSES_PAGE));
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/approve", method = RequestMethod.GET)
    public ModelAndView approve(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canApprove = courseService.canApprove(course);
        if (canApprove) {
            ModelAndView mav = new ModelAndView(APPROVE_VIEW);
            mav.addObject("decisionForm", new DecisionForm());
            mav.addObject(HEADER_TITLE, "Approve Course");
            mav.addObject(SINGLE_COURSE_KEY, courseApproveDTOConverter.convert(course));
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/approve", method = RequestMethod.POST)
    public ModelAndView approve(@PathVariable("id") int id,
                                @Valid @ModelAttribute("decisionForm") DecisionForm decisionForm,
                                BindingResult results) {
        Course course = courseService.getById(id);
        if (results.hasErrors()) {
            ModelAndView mav = new ModelAndView(APPROVE_VIEW);
            mav.addObject(HEADER_TITLE, "Approve Course");
            mav.addObject(ERRORS_KEY, results.getAllErrors());
            mav.addObject(SINGLE_COURSE_KEY, courseApproveDTOConverter.convert(course));
            return mav;
        } else {
            Decision decision = decisionConverter.convert(decisionForm);
            decisionService.makeDecision(decision, course);
            return new ModelAndView(new RedirectView(COURSES_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        if (courseService.canDelete(course)) {
            ModelAndView mav = new ModelAndView(DELETE_COURSE_VIEW);
            mav.addObject(HEADER_TITLE, "Delete Course");
            mav.addObject(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deletePost(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        if (courseService.canDelete(course)) {
            courseService.delete(course);
            return new ModelAndView(new RedirectView(COURSES_PAGE));
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/start", method = RequestMethod.GET)
    public ModelAndView start(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canStart = courseService.canStart(course);
        if (courseService.isOwner(course) && course.isReady()) {
            ModelAndView mav = new ModelAndView(START_COURSE_VIEW);
            mav.addObject(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
            mav.addObject(HEADER_TITLE, "Start course");
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/start", method = RequestMethod.POST)
    public ModelAndView startPost(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        courseService.makeInProgress(course);
        return new ModelAndView(new RedirectView(COURSES_PAGE));
    }

    @RequestMapping(value = "/courses/{id}/finish", method = RequestMethod.GET)
    public ModelAndView finish(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        boolean canFinish = courseService.canFinish(course);
        if (canFinish) {
            ModelAndView mav = new ModelAndView(FINISH_COURSE_VIEW);
            mav.addObject(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
            mav.addObject(HEADER_TITLE, "Finish course");
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/finish", method = RequestMethod.POST)
    public ModelAndView finishPost(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        courseService.makeFinished(course);
        return new ModelAndView(new RedirectView(COURSES_PAGE));
    }

    @RequestMapping(value = "/courses/{id}/notify", method = RequestMethod.GET)
    public ModelAndView notify(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        if (courseService.isOwner(course) && course.isFinished()) {
            ModelAndView mav = new ModelAndView(NOTIFY_COURSE_VIEW);
            mav.addObject(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
            mav.addObject(HEADER_TITLE, "Notify attendees");
            return mav;
        } else {
            return new ModelAndView(new RedirectView(ACCESS_DENIED_PAGE));
        }
    }

    @RequestMapping(value = "/courses/{id}/notify", method = RequestMethod.POST)
    public ModelAndView notifyPost(@PathVariable("id") int id) {
        Course course = courseService.getById(id);
        mailSender.sendEvaluateCourseNotification(course);
        return new ModelAndView(new RedirectView(COURSES_PAGE));
    }
}
