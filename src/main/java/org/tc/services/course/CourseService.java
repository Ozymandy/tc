package org.tc.services.course;

import org.tc.models.Course;
import org.tc.models.User;
import org.tc.models.usercourse.SubscribersCourse;

import java.util.List;

public interface CourseService {
    void create(Course newCourse);

    void delete(Course course);

    List<Course> getAll();

    Course getById(int id);

    void update(Course changedCourse);

    boolean isOwner(Course course);
}
