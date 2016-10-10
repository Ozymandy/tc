package org.tc.dao.role;

import org.springframework.stereotype.Repository;
import org.tc.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDao implements RoleDaoInterface {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Role newRole) {
        entityManager.persist(newRole);
    }

    @Override
    public void delete(Role role) {
        if (entityManager.contains(role)) {
            entityManager.remove(role);
        } else {
            entityManager.remove(entityManager.merge(role));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getAll() {
        return entityManager.createQuery("from Role").getResultList();
    }

    @Override
    public Role getById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getByName(String roleName) {
        return (Role) entityManager
                .createQuery("from tc.Role where name=:name")
                .setParameter("name", roleName);
    }

    @Override
    public void update(Role changedRole) {
        entityManager.merge(changedRole);
    }
}
