package org.tc.utils.converters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tc.models.Course;
import org.tc.models.forms.CourseUpdateForm;

import java.util.Optional;

@Component
public class CourseUpdateConverter extends CourseConverter {
    private static final Logger LOG = LoggerFactory.getLogger(CourseUpdateConverter.class);
    public Course convertToCourse(CourseUpdateForm source) {
        Course course = super.convertToCourse(source);
        LOG.debug(String.format("converting courseUpdateForm to Course with id %1$d",source.getCourseId()));
        course.setMinSubscribers(source.getMinSubscribers());
        return course;
    }

    public CourseUpdateForm convertToUpdateForm(Course source) {
        CourseUpdateForm updateForm = new CourseUpdateForm();
        populateCourseForm(source,updateForm);
        updateForm.setMinSubscribers(source.getMinSubscribers());
        return updateForm;
    }
}
