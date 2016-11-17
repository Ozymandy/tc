package org.tc.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.services.course.CourseService;
import org.tc.services.role.RoleService;
import org.tc.services.user.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailNotificationSender {
    private static final Logger LOG = LoggerFactory.getLogger(MailNotificationSender.class);
    private static final String COURSE_ANNOUNCEMENT_SUBJECT =
            "Course Announcement";
    private static final String COURSE_APPROVAL_UPDATE_SUBJECT =
            "Course Approval Update";
    private static final String NEW_COURSE_NOTIFICATION_SUBJECT = "New Course Added";
    private static final String REJECTED_COURSE_NOTIFICATION_SUBJECT = "Course rejected";
    private static final String DELETED_COURSE_NOTIFICATION_SUBJECT = "Course Deleted";
    private static final String OPEN_COURSE_NOTIFICATION_SUBJECT = "Course opened";
    private static final String READY_COURSE_NOTIFICATION_SUBJECT = "Course is ready to start";

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private CourseService courseService;
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
            String[] emailsTo = {roleService.getDepartmentManager().get().getEmail(),
                    roleService.getKnowledgeManager().get().getEmail()};
            helper.setTo(emailsTo);
            helper.setCc(course.getOwner().getEmail());
            helper.setText(contentBuilder.buildManagerNotification(course), true);
        } catch (MessagingException e) {
            LOG.debug(String.format("Message didn't send on courseId %1$d", course.getId()));
        }
        javaMailSender.send(message);

    }

    public void sendApprovalUpdate(Decision decision) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(COURSE_APPROVAL_UPDATE_SUBJECT);
            helper.setTo(decision.getCourseForReview().getOwner().getEmail());
            String[] emailsCc = {roleService.getDepartmentManager().get().getEmail(),
                    roleService.getKnowledgeManager().get().getEmail()};
            helper.setCc(emailsCc);
            helper.setText(contentBuilder.buildCourseApprovalUpdate(decision), true);
        } catch (MessagingException e) {
            LOG.debug(String.format("Message didn't send on courseId %1$d", decision.getCourseForReview().getId()));
        }
        javaMailSender.send(message);
    }

    public void sendNewCourseNotification(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(NEW_COURSE_NOTIFICATION_SUBJECT);
            String[] emailsCc = {roleService.getDepartmentManager().get().getEmail(),
                    roleService.getKnowledgeManager().get().getEmail()};
            helper.setTo(userService.getAllEmails());
            helper.setCc(emailsCc);
            helper.setText(contentBuilder.buildNewCourseNotification(course), true);
        } catch (MessagingException e) {
            LOG.debug(String.format("Message didn't send on courseId %1$d", course.getId()));
        }
        javaMailSender.send(message);
    }

    public void sendRejectedCourseNotification(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(REJECTED_COURSE_NOTIFICATION_SUBJECT);
            helper.setTo(course.getOwner().getEmail());
            String[] emailsCc = {roleService.getDepartmentManager().get().getEmail(),
                    roleService.getKnowledgeManager().get().getEmail()};
            helper.setCc(emailsCc);
            helper.setText(contentBuilder.buildRejectedCourseNotification(course), true);
        } catch (MessagingException e) {
            LOG.debug(String.format("Message didn't send on courseId %1$d", course.getId()));
        }
        javaMailSender.send(message);
    }

    public void sendDeletedCourseNotification(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(DELETED_COURSE_NOTIFICATION_SUBJECT);
            String[] emailsTo = {roleService.getDepartmentManager().get().getEmail(),
                    roleService.getKnowledgeManager().get().getEmail()};
            helper.setTo(emailsTo);
            helper.setCc(course.getOwner().getEmail());
            helper.setText(contentBuilder.buildDeletedCourseNotification(course), true);
        } catch (MessagingException e) {
            LOG.debug(String.format("Message didn't send on courseId %1$d", course.getId()));
        }
        javaMailSender.send(message);
    }

    public void sendOpenCourseNotification(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(OPEN_COURSE_NOTIFICATION_SUBJECT);
            List<String> listEmails = courseService.getSubscribersEmails(course);
            String[] emailsTo = listEmails.toArray(new String[listEmails.size()]);
            helper.setTo(emailsTo);
            helper.setCc(course.getOwner().getEmail());
            helper.setText(contentBuilder.buildOpenCourseNotification(course), true);
        } catch (MessagingException e) {
            LOG.debug(String.format("Message didn't send on courseId %1$d", course.getId()));
        }
        javaMailSender.send(message);
    }

    public void sendReadyCourseNotification(Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(READY_COURSE_NOTIFICATION_SUBJECT);
            helper.setTo(course.getOwner().getEmail());
            helper.setText(contentBuilder.buildReadyCourseNotification(course), true);
        } catch (MessagingException e) {
            LOG.debug(String.format("Message didn't send on courseId %1$d", course.getId()));
        }
        javaMailSender.send(message);
    }
}
