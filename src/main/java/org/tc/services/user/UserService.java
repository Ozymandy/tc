package org.tc.services.user;

import org.tc.models.Category;
import org.tc.models.Course;
import org.tc.models.User;

import java.util.List;

public interface UserService {

    void create(User newUser);

    List<User> getAll();

    User getCurrentUser();

    User getByName(String username);

    void update(User changedUser);

    boolean isAttendee(Course course);

    boolean isSubscribed(Course course);

    boolean isEvaluated(Course course);

    List<Course> getMyCourseList();

    List<Course> getMyCourseListByCategory(Category category);

    boolean isManager();

    boolean isKnowLedgeManager();

    boolean isDepartmentManager();

    boolean isVoted(Course course);
}
