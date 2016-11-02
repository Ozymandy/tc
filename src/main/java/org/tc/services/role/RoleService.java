package org.tc.services.role;

import org.tc.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    Role getByName(String name);
}
