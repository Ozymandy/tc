package org.tc.services.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.course.CourseDaoInterface;
import org.tc.models.Course;

import java.util.List;

@Service
public class CourseService implements CourseServiceInterface {
    @Autowired
    private CourseDaoInterface courseDao;
    @Override
    public void create(Course newCourse) {
        courseDao.create(newCourse);
    }

    @Override
    public void delete(Course course) {
        courseDao.delete(course);
    }

    @Override
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @Override
    public Course getById(int id) {
        return courseDao.getById(id);
    }

    @Override
    public void update(Course changedCourse) {
        courseDao.update(changedCourse);
    }
}
