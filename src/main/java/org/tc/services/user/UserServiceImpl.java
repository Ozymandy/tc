package org.tc.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tc.dao.user.UserDao;
import org.tc.models.Category;
import org.tc.models.Course;
import org.tc.models.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private static final String KNOWLEDGE_MANAGER_ROLE_NAME = "Knowledge Manager";
    private static final String DEPARTMENT_MANAGER_ROLE_NAME = "Department Manager";
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDao userDao;

    @Override
    public void create(User newUser) {
        String password = bCryptPasswordEncoder
                .encode(newUser.getPassword());
        newUser.setPassword(password);
        userDao.create(newUser);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }


    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        return userDao.getByName(auth.getName());
    }

    @Override
    public User getByName(String username) {
        return userDao.getByName(username);
    }

    @Override
    public void update(User changedUser) {
        changedUser.setPassword(bCryptPasswordEncoder
                .encode(changedUser.getPassword()));
        userDao.update(changedUser);
    }

    @Override
    public boolean isSubscribed(Course course) {
        User user = this.getCurrentUser();
        return user.getCoursesSubscribe().stream().anyMatch(usercourse ->
                usercourse.getCourse().getId() == course.getId());
    }

    @Override
    public boolean isAttendee(Course course) {
        User user = this.getCurrentUser();
        return user.getCoursesAttend().stream().anyMatch(usercourse ->
                usercourse.getCourse().getId() == course.getId());
    }

    @Override
    public boolean isEvaluated(Course course) {
        User user = this.getCurrentUser();
        return course.getEvaluations().stream().anyMatch(evaluation ->
                evaluation.getUser().getId() == user.getId());
    }

    @Override
    public List<Course> getMyCourseList() {
        User user = getCurrentUser();
        List<Course> myCourses = Stream.concat(user.getCoursesAttend().stream(),
                user.getCoursesSubscribe().stream())
                .map(course -> course.getCourse()).distinct().collect(Collectors.toList());
        myCourses = Stream.concat(myCourses.stream(),
                user.getOwnedCourses().stream()).collect(Collectors.toList());
        return myCourses;
    }

    @Override
    public List<Course> getMyCourseListByCategory(Category category) {
        return getMyCourseList().stream().filter(course -> course.getCategory()
                .getCategoryName().equals(category.getCategoryName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isManager() {
        return isDepartmentManager() || isKnowLedgeManager();
    }

    @Override
    public boolean isKnowLedgeManager() {
        User user = getCurrentUser();
        return user.getRole().getName().equals(KNOWLEDGE_MANAGER_ROLE_NAME);
    }

    @Override
    public boolean isDepartmentManager() {
        User user = getCurrentUser();
        return user.getRole().getName().equals(DEPARTMENT_MANAGER_ROLE_NAME);
    }

}
