package org.tc.services.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.evaluation.EvaluationDaoInterface;
import org.tc.models.Evaluation;

import java.util.List;

@Service
public class EvaluationService implements EvaluationServiceInterface {
    @Autowired
    private EvaluationDaoInterface evaluationDao;

    @Override
    public void create(Evaluation newEvaluation) {
        evaluationDao.create(newEvaluation);
    }

    @Override
    public void delete(Evaluation evaluation) {
        evaluationDao.delete(evaluation);
    }

    @Override
    public List<Evaluation> getAll() {
        return evaluationDao.getAll();
    }

    @Override
    public List<Evaluation> getByUserId(int id) {
        return evaluationDao.getByUserId(id);
    }

    @Override
    public List<Evaluation> getByCourseId(int id) {
        return evaluationDao.getByCourseId(id);
    }

    @Override
    public void update(Evaluation changedEvaluation) {
        evaluationDao.update(changedEvaluation);
    }
}
