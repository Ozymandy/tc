package org.tc.dao.category;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Category newCategory) {
        entityManager.persist(newCategory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> getAll() {
        return entityManager.createQuery("from Category").getResultList();
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return (Category) entityManager
                .createQuery("from Category where CategoryName=:name")
                .setParameter("name", categoryName).getSingleResult();
    }

    @Override
    public Category getByCategoryId(int id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public void delete(Category category) {
        if (entityManager.contains(category)) {
            entityManager.remove(category);
        } else {
            entityManager.remove(entityManager.merge(category));
        }
    }
}
