package org.tc.services.role;

import org.tc.models.Role;

import java.util.List;

public interface RoleServiceInterface {
    void create(Role newRole);
    void delete(Role role);
    List<Role> getAll();
    Role getById(int id);
    Role getByName(String name);
    void update(Role changedRole);
}
