package org.tc.dao.role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tc.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {
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
                .createQuery("from Role where name=:name")
                .setParameter("name", roleName).getSingleResult();
    }

    @Override
    public void update(Role changedRole) {
        entityManager.merge(changedRole);
    }
}
