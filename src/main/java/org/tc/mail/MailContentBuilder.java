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
    private static final String ONE_COURSE_OBJECT_NAME = "courses";
    private static final String ONE_DECISION_OBJECT_NAME = "decision";
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private CourseDetailsDTOConverter courseDetailsDTOConverter;

    public String buildManagerNotification(Course course) {
        Context context = new Context();
        context.setVariable(ONE_COURSE_OBJECT_NAME,
                courseDetailsDTOConverter.convert(course));
        return templateEngine.process(MANAGER_NOTIFICATION_VIEW_NAME, context);
    }

    public String buildCourseApprovalUpdate(Decision decision) {
        Context context = new Context();
        context.setVariable(ONE_DECISION_OBJECT_NAME, decision);
        return templateEngine.process(COURSE_APPROVAL_UPDATE_VIEW_NAME, context);
    }
}
