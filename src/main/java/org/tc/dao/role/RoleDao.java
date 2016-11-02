package org.tc.dao.role;

import org.tc.models.Role;

import java.util.List;

public interface RoleDao {

    List<Role> getAll();

    Role getByName(String roleNme);
}
