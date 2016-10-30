package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tc.models.Course;
import org.tc.models.forms.CourseForm;
import org.tc.services.user.UserService;

@Component
public class CourseConverter implements Converter<CourseForm, Course> {

    @Autowired
    private UserService userService;

    @Override
    public Course convert(CourseForm source) {
        Course course = new Course();
        course.setName(source.getName());
        course.setDescription(source.getDescription());
        course.setLinks(source.getLinks());
        course.setUser(userService.getByName(source.getUser()));
        return course;
    }
}
