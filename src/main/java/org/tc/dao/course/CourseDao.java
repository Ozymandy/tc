package org.tc.dao.course;

import org.tc.models.Course;

import java.util.List;

public interface CourseDao {
    void create(Course newCourse);

    void delete(Course course);

    List<Course> getAll();

    Course getById(int id);

    Course getByName(String courseName);

    void update(Course changedCourse);
}
