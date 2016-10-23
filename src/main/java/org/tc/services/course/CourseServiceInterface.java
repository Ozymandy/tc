package org.tc.services.course;

import org.tc.models.Course;

import java.util.List;

public interface CourseServiceInterface {
    void create(Course newCourse);

    void delete(Course course);

    List<Course> getAll();

    Course getById(int id);

    void update(Course changedCourse);

    boolean isOwner(String username, int courseId);
}