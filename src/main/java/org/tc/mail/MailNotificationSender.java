package org.tc.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.tc.exceptions.ManagerNotFoundException;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.services.course.CourseService;
import org.tc.services.role.RoleService;
import org.tc.services.user.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@PropertySource(value = "classpath:messages.properties")
public class MailNotificationSender {
    private static final Logger LOG = LoggerFactory.getLogger(MailNotificationSender.class);
    @Value("${Announcement.Course.Notification}")
    private String COURSE_ANNOUNCEMENT_SUBJECT;
    @Value("${Approval.Update.Course.Notification}")
    private String COURSE_APPROVAL_UPDATE_SUBJECT;
    @Value("${New.Course.Notification}")
    private String NEW_COURSE_NOTIFICATION_SUBJECT;
    @Value("${Rejected.Course.Notification}")
    private String REJECTED_COURSE_NOTIFICATION_SUBJECT;
    @Value("${Deleted.Course.Notification}")
    private String DELETED_COURSE_NOTIFICATION_SUBJECT;
    @Value("${Opened.Course.Notification}")
    private String OPEN_COURSE_NOTIFICATION_SUBJECT;
    @Value("${Ready.Course.Notification}")
    private String READY_COURSE_NOTIFICATION_SUBJECT;
    @Value("${Started.Course.Notification}")
    private String STARTED_COURSE_NOTIFICATION_SUBJECT;
    @Value("${Finished.Course.Notification}")
    private String FINISHED_COURSE_NOTIFICATION_SUBJECT;
    @Value("${Evaluation.Course.Notification}")
    private String EVALUATION_RE_ASK_SUBJECT;

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
        String[] emailsTo = {roleService.getDepartmentManager().getEmail(),
                roleService.getKnowledgeManager().getEmail()};
        String[] emailsCc = {course.getOwner().getEmail()};
        sendMessage(contentBuilder.buildManagerNotification(course),
                emailsTo, emailsCc, COURSE_ANNOUNCEMENT_SUBJECT, course);

    }

    public void sendApprovalUpdate(Decision decision) {
        String[] emailsCc = {roleService.getDepartmentManager().getEmail(),
                roleService.getKnowledgeManager().getEmail()};
        String[] emailsTo = {decision.getCourseForReview().getOwner().getEmail()};
        sendMessage(contentBuilder.buildCourseApprovalUpdate(decision),
                emailsTo, emailsCc, COURSE_APPROVAL_UPDATE_SUBJECT,
                decision.getCourseForReview());
    }

    public void sendNewCourseNotification(Course course) {
        String[] emailsCc = {roleService.getDepartmentManager().getEmail(),
                roleService.getKnowledgeManager().getEmail()};
        String[] emailsTo = userService.getAllEmails();
        sendMessage(contentBuilder.buildNewCourseNotification(course),
                emailsTo, emailsCc, NEW_COURSE_NOTIFICATION_SUBJECT, course);
    }

    public void sendRejectedCourseNotification(Course course) {
        String[] emailsTo = {course.getOwner().getEmail()};
        String[] emailsCc = {roleService.getDepartmentManager().getEmail(),
                roleService.getKnowledgeManager().getEmail()};
        sendMessage(contentBuilder.buildRejectedCourseNotification(course),
                emailsTo, emailsCc, REJECTED_COURSE_NOTIFICATION_SUBJECT, course);
    }

    public void sendDeletedCourseNotification(Course course) {
        String[] emailsCc = {course.getOwner().getEmail()};
        String[] emailsTo = {roleService.getDepartmentManager().getEmail(),
                roleService.getKnowledgeManager().getEmail()};
        sendMessage(contentBuilder.buildDeletedCourseNotification(course),
                emailsTo, emailsCc, DELETED_COURSE_NOTIFICATION_SUBJECT, course);
    }

    public void sendOpenCourseNotification(Course course) {
        String[] emailsCc = {course.getOwner().getEmail()};
        List<String> listEmails = courseService.getSubscribersEmails(course);
        String[] emailsTo = listEmails.toArray(new String[listEmails.size()]);
        sendMessage(contentBuilder.buildOpenCourseNotification(course), emailsTo,
                emailsCc, OPEN_COURSE_NOTIFICATION_SUBJECT, course);
    }

    public void sendReadyCourseNotification(Course course) {
        String[] emailsTo = {course.getOwner().getEmail()};
        sendMessage(contentBuilder.buildReadyCourseNotification(course), emailsTo,
                READY_COURSE_NOTIFICATION_SUBJECT, course);
    }

    public void sendStartedCourseNotification(Course course) {
        List<String> listEmails = courseService.getAttendeeEmails(course);
        String[] emailsTo = listEmails.toArray(new String[listEmails.size()]);
        sendMessage(contentBuilder.buildStartedCourseNotification(course), emailsTo,
                STARTED_COURSE_NOTIFICATION_SUBJECT, course);
    }

    public void sendFinishedCourseNotification(Course course) {
        List<String> listEmails = courseService.getAttendeeEmails(course);
        String[] emailsTo = listEmails.toArray(new String[listEmails.size()]);
        sendMessage(contentBuilder.buildFinishedCourseNotification(course),
                emailsTo, FINISHED_COURSE_NOTIFICATION_SUBJECT, course);
    }

    public void sendEvaluateCourseNotification(Course course) {
        List<String> listEmails =
                courseService.getNotEvaluatedAttendeesEmails(course);
        if (!listEmails.isEmpty()) {
            String[] emailsTo = listEmails.toArray(new String[listEmails.size()]);
            sendMessage(contentBuilder.buildEvaluationCourseNotification(course),
                    emailsTo, EVALUATION_RE_ASK_SUBJECT, course);
        }
    }

    private void sendMessage(String text, String[] emailsTo, String[] emailsCc,
                             String subject, Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setTo(emailsTo);
            helper.setCc(emailsCc);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOG.warn("Message didn't send on notification type: {} with course id{}",
                    subject, course.getId(), e);
        } catch (ManagerNotFoundException ex) {
            LOG.warn("Message didn't send on notification type: {} because manager not found",
                    subject, ex);
        }
    }

    private void sendMessage(String text, String[] emailsTo, String subject,
                             Course course) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setTo(emailsTo);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOG.warn("Message didn't send on notification type: {} with course id {}",
                    subject, course.getId(), e);
        } catch (ManagerNotFoundException ex) {
            LOG.warn("Message didn't send on notification type: {} because manager not found",
                    subject, ex);
        }
    }
}
