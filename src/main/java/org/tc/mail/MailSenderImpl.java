package org.tc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.tc.models.Course;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailSenderImpl {
    private static final String COURSE_ANNOUNCEMENT_SUBJECT =
            "Course Announcement";
    private static final String MANAGER_EMAIL ="mail@mail.com";
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailContentBuilder contentBuilder;
    public void send(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(COURSE_ANNOUNCEMENT_SUBJECT);
            helper.setTo(MANAGER_EMAIL);
            helper.setCc(course.getOwner().getEmail());
            helper.setText(contentBuilder.build(course), true);
        } catch (MessagingException e) {

        }
        javaMailSender.send(message);

    }
}
