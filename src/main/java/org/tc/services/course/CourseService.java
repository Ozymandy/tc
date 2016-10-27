package org.tc.services.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.course.CourseDaoInterface;
import org.tc.dao.user.UserDaoInterface;
import org.tc.exceptions.CourseNotFoundException;
import org.tc.models.Course;
import org.tc.models.User;
import org.tc.models.usercourse.Subscribers;

import java.util.List;
import java.util.stream.Collectors;

@Service("courseService")
public class CourseService implements CourseServiceInterface {
    @Autowired
    private CourseDaoInterface courseDao;
    @Autowired
    private UserDaoInterface userDao;

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
    public boolean isOwner(String username, int courseId) {
        User user = userDao.getByName(username);
        Course course = courseDao.getById(courseId);
        if (course != null && (user.getId() == course.getUser().getId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isSubscribed(String username, int courseId) {
        User user = userDao.getByName(username);
        Course course = courseDao.getById(courseId);
        return user.getCoursesSubscribe().stream().anyMatch(usercourse ->
                usercourse.getCourse().getId() == course.getId());
    }

    @Override
    public boolean isAttendee(String username, int courseId) {
        User user = userDao.getByName(username);
        Course course = courseDao.getById(courseId);
        return user.getCoursesAttend().stream().anyMatch(usercourse ->
                usercourse.getCourse().getId() == course.getId());
    }

    @Override
    public boolean isEvaluated(String username, int courseId) {
        User user = userDao.getByName(username);
        Course course = courseDao.getById(courseId);
        return course.getEvaluations().stream().anyMatch(evaluation ->
                evaluation.getUser().getId() == user.getId());
    }

    @Override
    public double getAverageGrade(int courseId) {
        Course course = courseDao.getById(courseId);
        return course.getEvaluations().stream()
                .mapToDouble(evaluation -> evaluation.getMark())
                .average().orElse(0);
    }

    @Override
    public List<Subscribers> getSubscribers(Course course) {
        //not sure that this is good idea to place this method here
        return course.getSubscribers().stream().filter(
                subscribers -> course.getAttendee().stream().noneMatch(
                        subscribers2 -> subscribers.getUser().getId() == subscribers2.getUser().getId()))
                .collect(Collectors.toList());
    }

}
