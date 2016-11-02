package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tc.dto.course.CourseDTO;
import org.tc.models.Course;
import org.tc.services.course.CourseService;
import org.tc.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CourseDTOConverter{
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    public CourseDTO convert(Course source) {
        CourseDTO dto = new CourseDTO();
        dto.setId(source.getId());
        dto.setCourseName(source.getName());
        dto.setAverageGrade(courseService.getAverageGrade(source));
        dto.setAttendeeCount(source.getAttendeeCourse().size());
        dto.setSubscribersCount(source.getSubscribers().size());
        dto.setCategoryName(source.getCategory().getCategoryName());
        dto.setSubscribed(userService.isSubscribed(source));
        dto.setAttendee(userService.isAttendee(source));
        dto.setEvaluated(userService.isEvaluated(source));
        dto.setIsOwner(courseService.isOwner(source));
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
