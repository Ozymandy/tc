package org.tc.services.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.course.CourseDao;
import org.tc.exceptions.CourseNotFoundException;
import org.tc.models.Course;
import org.tc.models.User;
import org.tc.services.user.UserService;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserService userService;

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
        Course course = courseDao.getById(id);
        if (course != null) {
            return course;
        } else {
            throw new CourseNotFoundException("Course not found");
        }
    }

    @Override
    public void update(Course changedCourse) {
        courseDao.update(changedCourse);
    }

    @Override
    public boolean isOwner(Course course) {
        User user = userService.getCurrentUser();
        if (course != null && (user.getId() == course.getUser().getId())) {
            return true;
        } else {
            return false;
        }
    }

}
