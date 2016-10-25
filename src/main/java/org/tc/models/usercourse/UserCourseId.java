package org.tc.models.usercourse;

import org.tc.models.Course;
import org.tc.models.User;

import java.io.Serializable;

public class UserCourseId implements Serializable {
    private User user;
    private Course course;

    @Override
    public int hashCode() {
        return (int) (user.getId() + course.getId());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof UserCourseId) {
            UserCourseId otherId = (UserCourseId) object;
            return (otherId.course.getId() == this.course.getId()) &&
                    (otherId.user.getId() == this.user.getId());
        }
        return false;
    }
}
