package org.tc.services.decision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.decision.DecisionDao;
import org.tc.models.Decision;

@Service
public class DecisionServiceImpl implements DecisionService {
    @Autowired
    private DecisionDao decisionDao;
    @Override
    public void create(Decision decision) {
        decisionDao.create(decision);
    }
}
