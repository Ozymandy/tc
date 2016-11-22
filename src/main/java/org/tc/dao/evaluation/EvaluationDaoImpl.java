package org.tc.dao.evaluation;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.Evaluation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class EvaluationDaoImpl implements EvaluationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Evaluation newEvaluation) {
        entityManager.persist(newEvaluation);
    }

    @Override
    public void update(Evaluation changedEvaluation) {
        entityManager.merge(changedEvaluation);
    }
}
