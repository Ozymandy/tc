package org.tc.services.usercourse;

import org.tc.models.usercourse.UserCourse;

import java.util.List;

public interface UserCourseServiceInterface {
    void create(UserCourse newUserCourse);

    void delete(UserCourse userCourse);

    List<UserCourse> getAll();

    List<UserCourse> getByUserId(int id);

    List<UserCourse> getByCourseId(int id);

    void update(UserCourse changedUserCourse);

}
