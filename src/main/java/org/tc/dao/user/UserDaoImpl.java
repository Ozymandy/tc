package org.tc.dao.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository(value = "userDao")
@Transactional
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User newUser) {
        entityManager.persist(newUser);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User getByName(String username) {
        try {
            return (User) entityManager
                    .createQuery("from User where username=:username")
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(User changedUser) {
        entityManager.merge(changedUser);
    }
}
