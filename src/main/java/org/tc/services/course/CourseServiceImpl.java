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
    public boolean isDraft(Course course) {
        return course.getState().equals(StateEnum.DRAFT);
    }

    @Override
    public boolean canViewCourse(Course course) {
        return isOwner(course) || !(isDraft(course) || isProposal(course) || isRejected(course))
                || userService.isManager();
    }

    @Override
    public void setProposal(Course course) {
        Course courseForReview = courseDao.getById(course.getId());
        courseForReview.setState(StateEnum.PROPOSAL);
        courseDao.update(courseForReview);
        mailSender.sendManagerNotification(courseForReview);
    }

    @Override
    public void setNew(Course course) {
        course.setState(StateEnum.NEW);
        courseDao.update(course);
    }

    @Override
    public void setRejected(Course course) {
        course.setState(StateEnum.REJECTED);
        courseDao.update(course);
    }

    @Override
    public void setOpen(Course course) {
        course.setState(StateEnum.OPEN);
        courseDao.update(course);
        mailSender.sendOpenCourseNotification(course);
    }

    @Override
    public void setReady(Course course) {
        course.setState(StateEnum.READY);
        courseDao.update(course);
        mailSender.sendReadyCourseNotification(course);
    }

    @Override
    public void processReviewResult(Course course) {
        Course reviewdCourse = courseDao.getById(course.getId());
        List<Decision> decisions = reviewdCourse.getDecisions();
        if (decisions.size() > 1) {
            boolean isApproved = decisions.stream().allMatch(decision ->
                    decision.getDecision() == DecisionEnum.APPROVE);
            if (isApproved) {
                setNew(reviewdCourse);
                mailSender.sendNewCourseNotification(reviewdCourse);
            } else {
                setRejected(reviewdCourse);
                mailSender.sendRejectedCourseNotification(reviewdCourse);
            }
        }
    }

    @Override
    public boolean isProposal(Course course) {
        return course.getState().equals(StateEnum.PROPOSAL);
    }

    @Override
    public boolean isRejected(Course course) {
        return course.getState().equals(StateEnum.REJECTED);
    }

    @Override
    public boolean isNew(Course course) {
        return course.getState().equals(StateEnum.NEW);
    }

    @Override
    public boolean isOpen(Course course) {
        return course.getState().equals(StateEnum.OPEN);
    }

    @Override
    public boolean isReady(Course course) {
        return course.getState().equals(StateEnum.READY);
    }

    @Override
    public boolean isInProgress(Course course) {
        return course.getState().equals(StateEnum.IN_PROGRESS);
    }

    @Override
    public boolean isFinished(Course course) {
        return course.getState().equals(StateEnum.FINISHED);
    }

    @Override
    public boolean canSubscribe(Course course) {
        return !userService.isSubscribed(course) && (isNew(course) ||
                isOpen(course) || isReady(course));
    }

    @Override
    public boolean canAttend(Course course) {
        return userService.isSubscribed(course) &&
                !userService.isAttendee(course) &&
                (isOpen(course) || isReady(course));
    }

    @Override
    public boolean canBeDeletedCourse(Course course) {
        return isOwner(course) && (isDraft(course) || isRejected(course));
    }

    @Override
    public void processSubscriptionCount(Course course) {
        //because we need updated course object with all subscribers
        Course courseForProcessing = getById(course.getId());
        boolean hasEnoughSubscribers = courseForProcessing.getSubscribers().size() >=
                courseForProcessing.getMinSubscribers();
        if (hasEnoughSubscribers) {
            setOpen(course);
        }
    }

    @Override
    public void processAttendeeCount(Course course) {
        Course courseForProcessing = getById(course.getId());
        boolean hasEnoughAttendees = courseForProcessing.getSubscribers().size() >=
                courseForProcessing.getMinSubscribers();
        if(hasEnoughAttendees){
            setReady(course);
        }
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
