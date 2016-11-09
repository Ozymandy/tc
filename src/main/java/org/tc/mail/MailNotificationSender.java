package org.tc.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.format.datetime.standard.DateTimeContext;
import org.springframework.format.datetime.standard.DateTimeContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.services.role.RoleService;
import org.tc.services.user.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailNotificationSender {
    private static final Logger LOG = LoggerFactory.getLogger(MailNotificationSender.class);
    private static final String COURSE_ANNOUNCEMENT_SUBJECT =
            "Course Announcement";
    private static final String COURSE_APPROVAL_UPDATE_SUBJECT =
            "Course Approval Update";
    private static final String NEW_COURSE_NOTIFICATION = "New Course Added";
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailContentBuilder contentBuilder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    public void sendManagerNotification(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(COURSE_ANNOUNCEMENT_SUBJECT);
            String[] emailsTo = {roleService.getDepartmentManager().getEmail(),
                    roleService.getKnowledgeManager().getEmail()};
            helper.setTo(emailsTo);
            helper.setCc(course.getOwner().getEmail());
            helper.setText(contentBuilder.buildManagerNotification(course), true);
        } catch (MessagingException e) {
            LOG.warn("Message didn't send on courseId {0}", course.getId());
        }
        javaMailSender.send(message);

    }

    public void sendApprovalUpdate(Decision decision) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(COURSE_APPROVAL_UPDATE_SUBJECT);
            helper.setTo(decision.getCourseForReview().getOwner().getEmail());
            String[] emailsTo = {roleService.getDepartmentManager().getEmail(),
                    roleService.getKnowledgeManager().getEmail()};
            helper.setCc(emailsTo);
            helper.setText(contentBuilder.buildCourseApprovalUpdate(decision), true);
        } catch (MessagingException e) {
            LOG.warn("Message didn't send on courseId {0}", decision.getCourseForReview().getId());
        }
        javaMailSender.send(message);
    }

    public void sendNewCourseNotification(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(NEW_COURSE_NOTIFICATION);
            String[] emailsTo = {roleService.getDepartmentManager().getEmail(),
                    roleService.getKnowledgeManager().getEmail()};
            helper.setTo(userService.getAllEmails());
            helper.setCc("ozymandy.k@gmail.com");
            helper.setText(contentBuilder.buildNewCourseNotification(course), true);
        } catch (MessagingException e) {
            LOG.warn("Message didn't send on courseId {0}", course.getId());
        }
        javaMailSender.send(message);

    }
}
