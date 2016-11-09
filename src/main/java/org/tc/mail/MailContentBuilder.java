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
    private static final String MANAGER_NOTIFICATION_VIEW_NAME =
            "mail/manager_notification";
    private static final String COURSE_APPROVAL_UPDATE_VIEW_NAME =
            "mail/course_approval_update";
    private static final String NEW_COURSE_NOTIFICATION_VIEW_NAME =
            "mail/new_course_notification";
    private static final String REJECTED_COURSE_NOTIFICATION_VIEW_NAME =
            "mail/rejected_course_notification";
    private static final String ONE_COURSE_OBJECT_NAME = "course";
    private static final String ONE_DECISION_OBJECT_NAME = "decision";
    private static final String DECISIONS_OBJECT_NAME = "decisions";
    private static final String COUNT_VOTES_OBJECT_NAME = "votes";
    private static final String APP_URL_OBJECT_NAME = "url";
    private static final String APP_URL = "http://localhost:8080/courses/";
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private CourseDetailsDTOConverter courseDetailsDTOConverter;

    public String buildManagerNotification(Course course) {
        Context context = new Context();
        context.setVariable(ONE_COURSE_OBJECT_NAME,
                courseDetailsDTOConverter.convert(course));
        context.setVariable(APP_URL_OBJECT_NAME, APP_URL);
        return templateEngine.process(MANAGER_NOTIFICATION_VIEW_NAME, context);
    }

    public String buildCourseApprovalUpdate(Decision decision) {
        Context context = new Context();
        context.setVariable(ONE_DECISION_OBJECT_NAME, decision);
        return templateEngine.process(COURSE_APPROVAL_UPDATE_VIEW_NAME, context);
    }

    public String buildNewCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(APP_URL_OBJECT_NAME, APP_URL);
        context.setVariable(ONE_COURSE_OBJECT_NAME, courseDetailsDTOConverter.convert(course));
        return templateEngine.process(NEW_COURSE_NOTIFICATION_VIEW_NAME, context);
    }
    public String buildRejectedCourseNotification(Course course) {
        Context context = new Context();
        context.setVariable(APP_URL_OBJECT_NAME, APP_URL);
        //specific string for view
        String votes = course.getDecisions().stream().allMatch(decision ->
                decision.getDecision().getValue().equals("Reject"))?"both votes":"one vote";
        context.setVariable(DECISIONS_OBJECT_NAME, course.getDecisions());
        context.setVariable(COUNT_VOTES_OBJECT_NAME,votes);
        return templateEngine.process(REJECTED_COURSE_NOTIFICATION_VIEW_NAME, context);
    }
}
