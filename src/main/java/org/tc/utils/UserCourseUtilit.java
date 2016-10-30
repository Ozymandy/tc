package org.tc.utils;

import org.springframework.stereotype.Component;
import org.tc.models.Course;
import org.tc.models.User;
import org.tc.models.usercourse.SubscribersCourse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserCourseUtilit {
    public boolean isSubscribed(User user, Course course) {
        return user.getCoursesSubscribe().stream().anyMatch(usercourse ->
                usercourse.getCourse().getId() == course.getId());
    }

    public boolean isAttendee(User user, Course course) {
        return user.getCoursesAttend().stream().anyMatch(usercourse ->
                usercourse.getCourse().getId() == course.getId());
    }

    public boolean isEvaluated(User user, Course course) {
        return course.getEvaluations().stream().anyMatch(evaluation ->
                evaluation.getUser().getId() == user.getId());
    }

    public double getAverageGrade(Course course) {
        return course.getEvaluations().stream()
                .mapToDouble(evaluation -> evaluation.getMark())
                .average().orElse(0);
    }

    public List<SubscribersCourse> getSubscribers(Course course) {
        return course.getSubscribers().stream().filter(
                subscribers -> course.getAttendeeCourse().stream().noneMatch(
                        subscribers2 -> subscribers.getUser().getId() == subscribers2.getUser().getId()))
                .collect(Collectors.toList());
    }
}
