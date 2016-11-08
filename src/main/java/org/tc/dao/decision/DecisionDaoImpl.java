package org.tc.dao.decision;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.Decision;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class DecisionDaoImpl implements DecisionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Decision decision) {
        entityManager.persist(decision);
    }
}
