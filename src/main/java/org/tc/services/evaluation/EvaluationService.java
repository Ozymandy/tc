package org.tc.services.evaluation;

import org.tc.models.Course;
import org.tc.models.Evaluation;

import java.util.List;

public interface EvaluationService {

    void evaluate(Course course, Evaluation evaluation);
}
