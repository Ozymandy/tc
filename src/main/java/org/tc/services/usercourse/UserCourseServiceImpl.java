package org.tc.services.usercourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.user.UserDao;
import org.tc.dao.usercourse.UserCourseDao;
import org.tc.models.Course;
import org.tc.models.User;
import org.tc.models.usercourse.AttendeeCourse;
import org.tc.models.usercourse.SubscribersCourse;
import org.tc.models.usercourse.UserCourse;
import org.tc.services.user.UserService;

import java.util.List;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    @Autowired
    private UserCourseDao userCourseDao;
    @Autowired
    private UserService userService;

    @Override
    public void subscribe(Course course) {
        User user = userService.getCurrentUser();
        SubscribersCourse subscriber = new SubscribersCourse();
        subscriber.setUser(user);
        subscriber.setCourse(course);
        userCourseDao.create(subscriber);
    }

    @Override
    public void attend(Course course) {
        User user = userService.getCurrentUser();
        AttendeeCourse attendee = new AttendeeCourse();
        attendee.setCourse(course);
        attendee.setUser(user);
        userCourseDao.create(attendee);
    }
}
