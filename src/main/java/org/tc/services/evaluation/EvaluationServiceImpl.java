package org.tc.services.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.evaluation.EvaluationDao;
import org.tc.models.Course;
import org.tc.models.Evaluation;
import org.tc.models.User;
import org.tc.services.user.UserService;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private UserService userService;
    @Autowired
    private EvaluationDao evaluationDao;


    @Override
    public void evaluate(Course course, Evaluation evaluation) {
        User user = userService.getCurrentUser();
        evaluation.setCourse(course);
        evaluation.setUser(user);
        evaluationDao.create(evaluation);
    }
}
