package org.tc.dao.usercourse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.usercourse.UserCourse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserCourseDaoImpl implements UserCourseDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(UserCourse newUserCourse) {
        entityManager.persist(newUserCourse);
    }
}
