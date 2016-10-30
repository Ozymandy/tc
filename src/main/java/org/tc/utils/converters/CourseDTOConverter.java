package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.tc.dto.CourseDTO;
import org.tc.models.Course;
import org.tc.models.User;
import org.tc.services.course.CourseService;
import org.tc.services.user.UserService;
import org.tc.utils.UserCourseUtilit;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CourseDTOConverter implements Converter<Course, CourseDTO> {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCourseUtilit userCourseUtil;
    @Override
    public CourseDTO convert(Course source) {
        CourseDTO dto = new CourseDTO();
        dto.setDescription(source.getDescription());
        dto.setId(source.getId());
        dto.setCourseName(source.getName());
        dto.setLinks(source.getLinks());
        User currentUser = userService.getCurrentUser();
        dto.setSubscribed(userCourseUtil
                .isSubscribed(currentUser,source));
        dto.setAttendee(userCourseUtil.isAttendee(currentUser,source));
        dto.setEvaluated(userCourseUtil.isEvaluated(currentUser,source));
        dto.setIsOwner(courseService.isOwner(source));
        //is it bad?
        String attendeeSubscriber =source.getSubscribers().size() +
                (source.getAttendeeCourse().size()>0?"\\"+source.getAttendeeCourse().size():"");
        dto.setAttendeeSubscriber(attendeeSubscriber);
        dto.setAverageMark(userCourseUtil.getAverageGrade(source));
        return dto;
    }

    public List<CourseDTO> convertAll(List<Course> courses) {
        Stream<Course> stream = courses.stream();
        return stream.map(course -> {
            return this.convert(course);
        })
                .collect(Collectors.toList());
    }
}
