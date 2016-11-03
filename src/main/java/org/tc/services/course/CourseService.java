package org.tc.services.course;

import org.tc.models.Course;
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

    boolean isDrafted(Course course);

    boolean canViewCourse(Course course);

    void setProposal(Course course);

    boolean isProposal(Course course);
}
