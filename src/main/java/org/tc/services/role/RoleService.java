package org.tc.services.role;

import org.tc.models.Role;
import org.tc.models.User;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAll();

    Role getByName(String name);

    Optional<User> getKnowledgeManager();

    Optional<User> getDepartmentManager();
}
