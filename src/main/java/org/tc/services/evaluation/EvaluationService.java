package org.tc.services.evaluation;

import org.tc.models.Course;
import org.tc.models.Evaluation;

import java.util.List;

public interface EvaluationService {
    void create(Evaluation newEvaluation);

    void delete(Evaluation evaluation);

    List<Evaluation> getAll();

    List<Evaluation> getByUserId(int id);

    List<Evaluation> getByCourseId(int id);

    void update(Evaluation changedEvaluation);

    void evaluate(Course course, Evaluation evaluation);
}
