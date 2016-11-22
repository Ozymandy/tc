package org.tc.dao.evaluation;

import org.tc.models.Evaluation;

import java.util.List;

public interface EvaluationDao {
    void create(Evaluation newEvaluation);

    void update(Evaluation changedEvaluation);

}
