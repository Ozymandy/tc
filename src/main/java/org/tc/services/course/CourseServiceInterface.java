package org.tc.services.course;

import org.tc.models.Course;

import java.util.List;

public interface CourseServiceInterface {
    public void create(Course newCourse);
    public void delete(Course course);
    public List<Course> getAll();
    public Course getById(int id);
    public void update(Course changedCourse);
}
