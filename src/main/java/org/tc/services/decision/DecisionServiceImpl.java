package org.tc.services.decision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.decision.DecisionDao;
import org.tc.models.Course;
import org.tc.models.Decision;
import org.tc.services.course.CourseService;
import org.tc.services.role.RoleService;
import org.tc.services.user.UserService;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class DecisionServiceImpl implements DecisionService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private DecisionDao decisionDao;
    @Autowired
    private RoleService roleService;

    @Override
    public void makeDecision(Decision decision, Course course) {
        decision.setCourseForReview(course);
        decisionDao.create(decision);
        courseService.setReviewDecision(course);
    }

    @Override
    public Optional<Decision> getKnowledgeManagerDecision(Course course) {
        Optional<Decision> optionalDecision = course.getDecisions().stream().filter(decision ->
                decision.getManager().getId() == roleService.getKnowledgeManager().getId()).findFirst();
        return optionalDecision;
    }

    @Override
    public Optional<Decision> getDepartmentManagerDecision(Course course) {
        Optional<Decision> optionalDecision = course.getDecisions().stream().filter(decision ->
                decision.getManager().getId() == roleService.getDepartmentManager().getId()).findFirst();
        return optionalDecision;
    }
}
