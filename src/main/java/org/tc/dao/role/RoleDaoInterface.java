package org.tc.dao.role;

import org.tc.models.Role;

import java.util.List;

public interface RoleDaoInterface {
    void create(Role newRole);

    void delete(Role role);

    List<Role> getAll();

    Role getById(int id);

    Role getByName(String roleNme);

    void update(Role changedRole);
}
