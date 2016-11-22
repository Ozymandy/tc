package org.tc.services.course;

import org.tc.models.Course;
import org.tc.models.usercourse.AttendeeCourse;
import org.tc.models.usercourse.SubscribersCourse;

import java.util.List;

public interface CourseService {
    void create(Course newCourse);

    void delete(Course course);

    List<Course> getAll();

    Course getById(int id);

    void update(Course changedCourse);

    boolean isOwner(Course course);

    double getAverageGrade(Course course);

    List<SubscribersCourse> getSubscribers(Course course);

    List<String> getSubscribersEmails(Course course);

    List<String> getAttendeeEmails(Course course);

    boolean canViewCourse(Course course);

    boolean canSubscribe(Course course);

    boolean canAttend(Course course);

    boolean canEvaluate(Course course);

    boolean canDelete(Course course);

    boolean canStart(Course course);

    boolean canFinish(Course course);

    boolean canUpdate(Course course);

    boolean canSendToReview(Course course);

    boolean canApprove(Course course);

    void makeProposal(Course course);

    void makeNew(Course course);

    void makeRejected(Course course);

    void makeOpen(Course course);

    void makeReady(Course course);

    void makeInProgress(Course course);

    void makeFinished(Course course);

    void processReviewResult(Course course);

    void processSubscriptionCount(Course course);

    void processAttendeeCount(Course course);

    List<String> getNotEvaluatedAttendeesEmails(Course course);

}
