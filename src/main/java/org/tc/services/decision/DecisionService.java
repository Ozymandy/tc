package org.tc.services.decision;

import org.tc.models.Course;
import org.tc.models.Decision;

import java.util.Optional;

public interface DecisionService {
    void makeDecision(Decision decision, Course course);

    Optional<Decision> getKnowledgeManagerDecision(Course course);

    Optional<Decision> getDepartmentManagerDecision(Course course);
}
