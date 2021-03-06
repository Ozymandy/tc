package org.tc.dao.course;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.Course;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CourseDaoImpl implements CourseDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Course newCourse) {
        entityManager.persist(newCourse);
    }

    @Override
    public void delete(Course course) {
        if (entityManager.contains(course)) {
            entityManager.remove(course);
        } else {
            entityManager.remove(entityManager.merge(course));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Course> getAll() {
        return entityManager.createQuery("from Course").getResultList();
    }

    @Override
    public Course getById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    public void update(Course changedCourse) {
        entityManager.merge(changedCourse);
    }
}
