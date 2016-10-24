package org.tc.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tc.models.Course;
import org.tc.models.forms.CourseForm;

@Component
public class CourseValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Course.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseForm course = (CourseForm) target;
        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors,
                        "name", "Blank.Course.Name");
        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors,
                        "description", "Blank.Course.Desc");
        ValidationUtils
                .rejectIfEmptyOrWhitespace(errors,
                        "links", "Blank.Course.Link");
        if (course.getName().length() < 2 ||
                course.getName().length() > 50) {
            errors.rejectValue("name", "Size.Course.Name");
        }
    }
}