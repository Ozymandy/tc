package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tc.dto.course.CourseDetailsDTO;
import org.tc.models.Course;
import org.tc.services.course.CourseService;
import org.tc.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CourseDetailsDTOConverter implements Converter<Course, CourseDetailsDTO> {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @Override
    public CourseDetailsDTO convert(Course source) {
        CourseDetailsDTO dto = new CourseDetailsDTO();
        dto.setId(source.getId());
        dto.setCourseName(source.getName());
        dto.setDescription(source.getDescription());
        dto.setLinks(source.getLinks());
        dto.setAttendeeCount(source.getAttendeeCourse().size());
        dto.setSubscribersCount(source.getSubscribers().size());
        dto.setCategoryName(source.getCategory().getCategoryName());
        dto.setOwnerEmail(source.getOwner().getEmail());
        dto.setSubscribersCourse(courseService.getSubscribersEmails(source));
        dto.setAverageGrade(courseService.getAverageGrade(source));
        dto.setAttendeeCourse(courseService.getAttendeeEmails(source));
        return dto;
    }

    public List<CourseDetailsDTO> convertAll(List<Course> courses) {
        Stream<Course> stream = courses.stream();
        return stream.map(course -> {
            return this.convert(course);
        })
                .collect(Collectors.toList());
    }
}
