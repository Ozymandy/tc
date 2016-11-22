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
import org.tc.models.enums.StateEnum;
import org.tc.models.usercourse.AttendeeCourse;
import org.tc.models.usercourse.SubscribersCourse;
import org.tc.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    @Autowired
    private MailNotificationSender mailSender;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserService userService;

    @Override
    public void create(Course newCourse) {
        newCourse.setState(StateEnum.DRAFT);
        courseDao.create(newCourse);
    }

    @Override
    public void delete(Course course) {
        courseDao.delete(course);
        mailSender.sendDeletedCourseNotification(course);
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
        changedCourseWithoutDelta.setMinSubscribers(changedCourse.getMinSubscribers());
        changedCourseWithoutDelta.setMinAttendees(changedCourse.getMinAttendees());
        courseDao.update(changedCourseWithoutDelta);
    }

    @Override
    public boolean canViewCourse(Course course) {
        return isOwner(course) || !(course.isDraft() || course.isProposal() || course.isRejected())
                || userService.isManager();
    }

    @Override
    public void makeProposal(Course course) {
        Course courseForReview = courseDao.getById(course.getId());
        courseForReview.setState(StateEnum.PROPOSAL);
        courseDao.update(courseForReview);
        mailSender.sendManagerNotification(courseForReview);
    }

    @Override
    public void makeNew(Course course) {
        course.setState(StateEnum.NEW);
        courseDao.update(course);
    }

    @Override
    public void makeRejected(Course course) {
        course.setState(StateEnum.REJECTED);
        courseDao.update(course);
    }

    @Override
    public void makeOpen(Course course) {
        course.setState(StateEnum.OPEN);
        courseDao.update(course);
        mailSender.sendOpenCourseNotification(course);
    }

    @Override
    public void makeReady(Course course) {
        course.setState(StateEnum.READY);
        courseDao.update(course);
        mailSender.sendReadyCourseNotification(course);
    }

    @Override
    public void makeInProgress(Course course) {
        course.setState(StateEnum.IN_PROGRESS);
        courseDao.update(course);
        mailSender.sendStartedCourseNotification(course);
    }

    @Override
    public void makeFinished(Course course) {
        course.setState(StateEnum.FINISHED);
        courseDao.update(course);
        mailSender.sendFinishedCourseNotification(course);
    }

    @Override
    public void processReviewResult(Course course) {
        Course reviewedCourse = courseDao.getById(course.getId());
        List<Decision> decisions = reviewedCourse.getDecisions();
        if (decisions.size() > 1) {
            boolean isApproved = decisions.stream().allMatch(decision ->
                    decision.getDecision() == DecisionEnum.APPROVE);
            if (isApproved) {
                makeNew(reviewedCourse);
                mailSender.sendNewCourseNotification(reviewedCourse);
            } else {
                makeRejected(reviewedCourse);
                mailSender.sendRejectedCourseNotification(reviewedCourse);
            }
        }
    }

    @Override
    public boolean canSubscribe(Course course) {
        return !userService.isSubscribed(course) && (course.isNew() ||
                course.isOpen() || course.isReady());
    }

    @Override
    public boolean canAttend(Course course) {
        return userService.isSubscribed(course) &&
                !userService.isAttendee(course) &&
                (course.isOpen() || course.isReady());
    }

    @Override
    public boolean canEvaluate(Course course) {
        return userService.isAttendee(course)
                && !userService.isEvaluated(course) && course.isFinished();
    }

    @Override
    public boolean canDelete(Course course) {
        return isOwner(course) && (course.isDraft() || course.isRejected());
    }

    @Override
    public boolean canStart(Course course) {
        return isOwner(course) && course.isReady();
    }

    @Override
    public boolean canFinish(Course course) {
        return course.isInProgress() && isOwner(course);
    }

    @Override
    public boolean canUpdate(Course course) {
        return isOwner(course) && (course.isDraft() || course.isRejected());
    }

    @Override
    public boolean canSendToReview(Course course) {
        return !course.isProposal() && isOwner(course);
    }

    @Override
    public boolean canApprove(Course course) {
        return course.isProposal() && userService.isManager();
    }

    @Override
    public void processSubscriptionCount(Course course) {
        //because we need updated course object with all subscribers
        Course courseForProcessing = getById(course.getId());
        boolean hasEnoughSubscribers = courseForProcessing.getSubscribers().size() >=
                courseForProcessing.getMinSubscribers();
        if (hasEnoughSubscribers) {
            makeOpen(course);
        }
    }

    @Override
    public void processAttendeeCount(Course course) {
        Course courseForProcessing = getById(course.getId());
        boolean hasEnoughAttendees = courseForProcessing.getSubscribers().size() >=
                courseForProcessing.getMinSubscribers();
        if (hasEnoughAttendees) {
            makeReady(course);
        }
    }

    @Override
    public List<String> getNotEvaluatedAttendeesEmails(Course course) {
        return course.getAttendeeCourse().stream().filter(attendee ->
                course.getEvaluations().stream().noneMatch(evaluation ->
                        evaluation.getUser().getId() ==
                                attendee.getUser().getId())).map(user ->
                user.getUser().getEmail()).collect(Collectors.toList());
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
