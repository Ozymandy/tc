package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.models.enums.DecisionEnum;
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
        decision.setDecision(source.getDecision());
        decision.setReason(source.getReason());
        return decision;
    }
}
