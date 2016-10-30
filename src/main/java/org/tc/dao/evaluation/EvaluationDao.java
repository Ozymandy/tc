package org.tc.dao.evaluation;

import org.tc.models.Evaluation;

import java.util.List;

public interface EvaluationDao {
    void create(Evaluation newEvaluation);

    void delete(Evaluation evaluation);

    List<Evaluation> getAll();

    List<Evaluation> getByUserId(int id);

    List<Evaluation> getByCourseId(int id);

    void update(Evaluation changedEvaluation);

}
