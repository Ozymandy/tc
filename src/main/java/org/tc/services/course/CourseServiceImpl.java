package org.tc.services.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.course.CourseDao;
import org.tc.exceptions.CourseNotFoundException;
import org.tc.mail.MailNotificationSender;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.models.User;
import org.tc.models.enums.DecisionEnum;
import org.tc.models.usercourse.SubscribersCourse;
import org.tc.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    private static final String DRAFT_COURSE = "Draft";
    private static final String PROPOSAL_COURSE = "Proposal";
    private static final String NEW_COURSE = "New";
    private static final String REJECTED_COURSE = "Rejected";
    @Autowired
    private MailNotificationSender mailSender;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserService userService;

    @Override
    public void create(Course newCourse) {
        newCourse.setState(DRAFT_COURSE);
        courseDao.create(newCourse);
    }

    @Override
    public void delete(Course course) {
        mailSender.sendDeletedCourseNotification(course);
        courseDao.delete(course);
    }

    @Override
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @Override
    public Course getById(int id) {
        Course course = courseDao.getById(id);
        if (course != null) {
            return course;
        } else {
            throw new CourseNotFoundException("Course not found");
        }
    }

    @Override
    public void update(Course changedCourse) {
        Course changedCourseWithoutDelta = getById(changedCourse.getId());
        changedCourseWithoutDelta.setCategory(changedCourse.getCategory());
        changedCourseWithoutDelta.setDescription(changedCourse.getDescription());
        changedCourseWithoutDelta.setLinks(changedCourse.getLinks());
        changedCourseWithoutDelta.setName(changedCourse.getName());
        courseDao.update(changedCourseWithoutDelta);
    }

    @Override
    public boolean isDrafted(Course course) {
        return course.getState().equals(DRAFT_COURSE);
    }

    @Override
    public boolean canBeViewedCourse(Course course) {
        return isOwner(course) || !(isDrafted(course) || isProposal(course) || isRejected(course))
                || userService.isManager();
    }

    @Override
    public void setProposal(Course course) {
        Course courseForReview = courseDao.getById(course.getId());
        courseForReview.setState(PROPOSAL_COURSE);
        mailSender.sendManagerNotification(courseForReview);
        courseDao.update(courseForReview);
    }

    @Override
    public void setNew(Course course) {
        Course newCourse = courseDao.getById(course.getId());
        newCourse.setState(NEW_COURSE);
        courseDao.update(newCourse);
    }

    @Override
    public void setRejected(Course course) {
        Course rejectedCourse = courseDao.getById(course.getId());
        rejectedCourse.setState(REJECTED_COURSE);
        courseDao.update(rejectedCourse);
    }

    @Override
    public void setReviewDecision(Course course) {
        Course reviewdCourse = courseDao.getById(course.getId());
        List<Decision> decisions = reviewdCourse.getDecisions();
        if (decisions.size() > 1) {
            boolean isApprove = decisions.stream().allMatch(decision ->
                    decision.getDecision() == DecisionEnum.APPROVE);
            if (isApprove) {
                mailSender.sendNewCourseNotification(reviewdCourse);
                setNew(reviewdCourse);
            } else {
                mailSender.sendRejectedCourseNotification(course);
                setRejected(reviewdCourse);
            }
        }
    }

    @Override
    public boolean isProposal(Course course) {
        return course.getState().equals(PROPOSAL_COURSE);
    }

    @Override
    public boolean isRejected(Course course) {
        return course.getState().equals(REJECTED_COURSE);
    }

    @Override
    public boolean canBeDeletedCourse(Course course) {
        return isOwner(course) && (isDrafted(course) || isRejected(course));
    }

    @Override
    public boolean isOwner(Course course) {
        User user = userService.getCurrentUser();
        if (course != null && (user.getId() == course.getOwner().getId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getAverageGrade(Course course) {
        return course.getEvaluations().stream()
                .mapToDouble(evaluation -> evaluation.getMark())
                .average().orElse(0);
    }

    @Override
    public List<SubscribersCourse> getSubscribers(Course course) {
        return course.getSubscribers().stream().filter(
                subscribers -> course.getAttendeeCourse().stream().noneMatch(
                        subscribers2 -> subscribers.getUser().getId() == subscribers2.getUser().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSubscribersEmails(Course course) {
        return this.getSubscribers(course).stream().map(subscribersCourse
                -> subscribersCourse.getUser().getEmail())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAttendeeEmails(Course course) {
        return course.getAttendeeCourse().stream().map(attendee
                -> attendee.getUser().getEmail()).collect(Collectors.toList());
    }

}
