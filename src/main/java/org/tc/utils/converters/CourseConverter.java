package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.tc.models.Course;
import org.tc.models.forms.CourseForm;
import org.tc.services.category.CategoryService;
import org.tc.services.course.CourseService;
import org.tc.services.user.UserService;

@Component
public class CourseConverter {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    public Course convertToCourse(CourseForm source) {
        Course course = new Course();
        course.setName(source.getName());
        course.setDescription(source.getDescription());
        course.setLinks(source.getLinks());
        course.setId(source.getCourseId());
        course.setCategory(categoryService
                .getCategoryByName(source.getCategory()));
        course.setOwner(userService.getByName(source.getUser()));
        return course;
    }

    public CourseForm convertToCourseForm(Course course) {
        CourseForm courseForm = new CourseForm();
        courseForm.setDescription(course.getDescription());
        courseForm.setLinks(course.getLinks());
        courseForm.setName(course.getName());
        courseForm.setCourseId(course.getId());
        return courseForm;
    }
}
