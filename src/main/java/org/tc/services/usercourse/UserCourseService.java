package org.tc.services.usercourse;

import org.tc.models.Course;
import org.tc.models.usercourse.UserCourse;

public interface UserCourseService {

    void subscribe(Course course);

    void attend(Course course);

}
