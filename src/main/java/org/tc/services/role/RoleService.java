package org.tc.services.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.role.RoleDaoInterface;
import org.tc.models.Role;

import java.util.List;

@Service
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleDaoInterface roleDao;
    @Override
    public void create(Role newRole) {
        roleDao.create(newRole);
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Role getById(int id) {
        return roleDao.getById(id);
    }

    @Override
    public Role getByName(String name) {
        return roleDao.getByName(name);
    }

    @Override
    public void update(Role changedRole) {
        roleDao.update(changedRole);

    }
}
