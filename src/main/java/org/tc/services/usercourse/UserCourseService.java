package org.tc.services.usercourse;

import org.tc.models.Course;
import org.tc.models.usercourse.UserCourse;

public interface UserCourseService {
    void create(UserCourse newUserCourse);

    void delete(UserCourse userCourse);

    void update(UserCourse changedUserCourse);

    void subscribe(Course course);

    void attend(Course course);

}
