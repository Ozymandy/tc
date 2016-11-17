package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tc.dto.course.CourseDTO;
import org.tc.models.Course;
import org.tc.services.course.CourseService;
import org.tc.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CourseDTOConverter {
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
        dto.setIsOwner(courseService.isOwner(source));
        dto.setState(source.getState().name());
        dto.setProposal(courseService.isProposal(source));
        dto.setDrafted(courseService.isDraft(source));
        dto.setRejected(courseService.isRejected(source));
        dto.setCanSubscribe(courseService.canSubscribe(source));
        dto.setNew(courseService.isNew(source));
        dto.setOpen(courseService.isOpen(source));
        dto.setReady(courseService.isReady(source));
        dto.setCanAttend(courseService.canAttend(source));
        dto.setInProgress(courseService.isInProgress(source));
        dto.setFinished(courseService.isFinished(source));
        dto.setCanEvaluate(courseService.canEvaluate(source));
        return dto;
    }

    public List<CourseDTO> convertAll(List<Course> courses) {
        Stream<Course> stream = courses.stream();
        return stream.filter(course -> courseService.canViewCourse(course)).map(course -> {
            return this.convert(course);
        })
                .collect(Collectors.toList());
    }
}
