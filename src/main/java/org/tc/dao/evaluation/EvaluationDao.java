package org.tc.dao.evaluation;

import org.tc.models.Evaluation;

import java.util.List;

public interface EvaluationDao {
    void create(Evaluation newEvaluation);

    List<Evaluation> getAll();

    void update(Evaluation changedEvaluation);

}
