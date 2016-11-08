package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.models.forms.DecisionForm;
import org.tc.services.course.CourseService;
import org.tc.services.user.UserService;

@Component
public class DecisionConverter {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    public Decision convert(DecisionForm source) {
        Decision decision = new Decision();
        decision.setManager(userService.getCurrentUser());
        //decision.setDecision();
        decision.setReason(source.getReason());
        decision.setCourseForReview(courseService.getById(source.getCourseId()));
        return decision;
    }

    public DecisionForm createForm(Course course) {
        DecisionForm form = new DecisionForm();
        form.setCourseId(course.getId());
        return form;
    }
}
