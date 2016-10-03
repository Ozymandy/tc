package org.tc.dao.course;

import org.tc.models.Course;

import java.util.List;

public interface CourseDaoInterface {
    public void create(Course newCourse);
    public void delete(Course course);
    public List<Course> getAll();
    public Course getById(int id);
    public Course getByName(String courseName);
    public void update(Course changedCourse);
}
