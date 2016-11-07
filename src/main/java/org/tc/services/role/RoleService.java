package org.tc.services.role;

import org.tc.models.Role;
import org.tc.models.User;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    Role getByName(String name);

    User getKnowledgeManager();

    User getDepartmentManager();
}
