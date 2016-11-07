package org.tc.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.models.Course;
import org.tc.utils.converters.CourseDetailsDTOConverter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
    private static final String MANAGER_NOTIFICATION_VIEW_NAME =
            "mail/manager_notification";
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private CourseDetailsDTOConverter courseDetailsDTOConverter;

    public String build(Course course) {
        Context context = new Context();
        context.setVariable("course", courseDetailsDTOConverter.convert(course));
        return templateEngine.process(MANAGER_NOTIFICATION_VIEW_NAME, context);
    }
}
