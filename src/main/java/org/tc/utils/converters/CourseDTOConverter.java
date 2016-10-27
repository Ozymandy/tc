package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.tc.dto.CourseDTO;
import org.tc.models.Course;
import org.tc.services.course.CourseServiceInterface;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CourseDTOConverter implements Converter<Course, CourseDTO> {
    @Autowired
    private CourseServiceInterface courseService;

    @Override
    public CourseDTO convert(Course source) {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        CourseDTO dto = new CourseDTO();
        dto.setDescription(source.getDescription());
        dto.setId(source.getId());
        dto.setCourseName(source.getName());
        dto.setLinks(source.getLinks());
        dto.setSubscribed(courseService.
                isSubscribed(auth.getName(), source.getId()));
        dto.setAttendee(courseService.isAttendee
                (auth.getName(), source.getId()));
        dto.setEvaluated(courseService.isEvaluated(auth.getName(),
                source.getId()));
        dto.setSubscribers(source.getSubscribers().size());
        dto.setIsOwner(courseService.isOwner(auth.getName(), source.getId()));
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
