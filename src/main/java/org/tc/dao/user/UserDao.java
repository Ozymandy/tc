package org.tc.dao.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository(value = "userDao")
@Transactional
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDao implements UserDaoInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User newUser) {
        entityManager.persist(newUser);
    }

    @Override
    public void delete(User user) {
        if (entityManager.contains(user)) {
            entityManager.remove(user);
        } else {
            entityManager.remove(entityManager.merge(user));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        return (User) entityManager
                .createQuery("from User where user.email=:email")
                .setParameter("email", email).getSingleResult();
    }

    @Override
    public User getByName(String username) {
        return (User) entityManager
                .createQuery("from User where username=:username")
                .setParameter("username", username);
    }

    @Override
    public void update(User changedUser) {
        entityManager.merge(changedUser);
    }
}
