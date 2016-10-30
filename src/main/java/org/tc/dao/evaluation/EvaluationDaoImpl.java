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
    public void delete(Evaluation evaluation) {
        if (entityManager.contains(evaluation)) {
            entityManager.remove(evaluation);
        } else {
            entityManager.remove(entityManager.merge(evaluation));
        }
    }

    @Override
    public List<Evaluation> getAll() {
        return entityManager.createQuery("from evaluation").getResultList();
    }

    @Override
    public List<Evaluation> getByUserId(int id) {
        return entityManager.createQuery("from evaluation where userid=:id")
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Evaluation> getByCourseId(int id) {
        return entityManager.createQuery("from evaluation where courseid=:id")
                .setParameter("id", id).getResultList();
    }

    @Override
    public void update(Evaluation changedEvaluation) {
        entityManager.merge(changedEvaluation);
    }
}
