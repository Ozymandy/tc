package org.tc.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.utils.converters.CourseDetailsDTOConverter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
    private static final String MANAGER_NOTIFICATION_VIEW =
            "mail/manager_notification";
    private static final String COURSE_APPROVAL_UPDATE_VIEW =
            "mail/course_approval_update";
    private static final String NEW_COURSE_NOTIFICATION_VIEW =
            "mail/new_course_notification";
    private static final String REJECTED_COURSE_NOTIFICATION_VIEW =
            "mail/rejected_course_notification";
    private static final String DELETED_COURSE_NOTIFICATION_VIEW =
            "mail/deleted_course_notification";
    private static final String OPEN_COURSE_NOTIFICATION_VIEW =
            "mail/open_course_notification";
    private static final String READY_COURSE_NOTIFICATION_VIEW =
            "mail/ready_course_notification";
    private static final String STARTED_COURSE_NOTIFICATION_VIEW =
            "mail/started_course_notification";
    private static final String FINISHED_COURSE_NOTIFICATION_VIEW =
            "mail/finished_course_notification";
    private static final String EVALUATE_COURSE_NOTIFICATION_VIEW =
            "mail/evaluate_course_notification";
    private static final String SINGLE_COURSE_KEY = "course";
    private static final String SINGLE_DECISION_KEY = "decision";
    private static final String DECISIONS_OBJECT_NAME = "decisions";
    private static final String COUNT_VOTES_KEY = "votes";
    private static final String APP_URL_KEY = "url";
    private static final String APP_URL = "http://localhost:8080/courses/";
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private CourseDetailsDTOConverter courseDetailsDTOConverter;

    public String buildManagerNotification(Course course) {
        Context context = new Context();
        context.setVariable(SINGLE_COURSE_KEY,
                courseDetailsDTOConverter.convert(course));
        context.setVariable(APP_URL_KEY, APP_URL);
        return templateEngine.process(MANAGER_NOTIFICATION_VIEW, context);
    }

    public String buildCourseApprovalUpdate(Decision decision) {
        Context context = new Context();
        context.setVariable(SINGLE_DECISION_KEY, decision);
        return templateEngine.process(COURSE_APPROVAL_UPDATE_VIEW, context);
    }

    public String buildNewCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(APP_URL_KEY, APP_URL);
        context.setVariable(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
        return templateEngine.process(NEW_COURSE_NOTIFICATION_VIEW, context);
    }

    public String buildRejectedCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(APP_URL_KEY, APP_URL);
        //specific string for view
        String votes = course.getDecisions().stream().allMatch(decision ->
                decision.getDecision().getValue().equals("Reject")) ? "both votes" : "one vote";
        context.setVariable(DECISIONS_OBJECT_NAME, course.getDecisions());
        context.setVariable(COUNT_VOTES_KEY, votes);
        return templateEngine.process(REJECTED_COURSE_NOTIFICATION_VIEW, context);
    }

    public String buildDeletedCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(SINGLE_COURSE_KEY, course);
        return templateEngine.process(DELETED_COURSE_NOTIFICATION_VIEW, context);
    }

    public String buildOpenCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
        return templateEngine.process(OPEN_COURSE_NOTIFICATION_VIEW, context);
    }

    public String buildReadyCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
        return templateEngine.process(READY_COURSE_NOTIFICATION_VIEW, context);
    }

    public String buildStartedCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
        return templateEngine.process(STARTED_COURSE_NOTIFICATION_VIEW, context);
    }

    public String buildFinishedCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
        return templateEngine.process(FINISHED_COURSE_NOTIFICATION_VIEW, context);
    }
    public String buildEvaluationCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(SINGLE_COURSE_KEY, courseDetailsDTOConverter.convert(course));
        return templateEngine.process(EVALUATE_COURSE_NOTIFICATION_VIEW, context);
    }
}
