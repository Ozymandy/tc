package org.tc.dao.usercourse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.usercourse.UserCourse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserCourseDao implements UserCourseDaoInterface {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(UserCourse newUserCourse) {
        entityManager.persist(newUserCourse);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(UserCourse userCourse) {
        if (entityManager.contains(userCourse)) {
            entityManager.remove(userCourse);
        } else {
            entityManager.remove(entityManager.merge(userCourse));
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<UserCourse> getAll() {
        return entityManager.createQuery("from Usercourse").getResultList();
    }

    @Override
    public List<UserCourse> getByUserId(int id) {
        return entityManager.createQuery("from Usercourse where userid=:id")
                .setParameter("id",id).getResultList();
    }

    @Override
    public List<UserCourse> getByCourseId(int id) {
        return entityManager.createQuery("from Usercourse where courseid=:id")
                .setParameter("id",id).getResultList();
    }


    @Override
    public void update(UserCourse changedUserCourse) {
        entityManager.merge(changedUserCourse);
    }
}
