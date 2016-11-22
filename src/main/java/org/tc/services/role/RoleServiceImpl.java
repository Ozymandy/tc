package org.tc.services.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.role.RoleDao;
import org.tc.exceptions.ManagerNotFoundException;
import org.tc.models.Role;
import org.tc.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private static final String KNOWLEDGE_MANAGER_ROLE_NAME = "Knowledge Manager";
    private static final String DEPARTMENT_MANAGER_ROLE_NAME = "Department Manager";

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Role getByName(String name) {
        return roleDao.getByName(name);
    }

    @Override
    public User getKnowledgeManager() {
        return getByName(KNOWLEDGE_MANAGER_ROLE_NAME).getUserListByRole().
                stream().findFirst()
                .orElseThrow(() -> new ManagerNotFoundException("Knowledge manager not found"));
    }

    @Override
    public User getDepartmentManager() {
        return getByName(DEPARTMENT_MANAGER_ROLE_NAME).getUserListByRole().stream().findFirst()
                .orElseThrow(() -> new ManagerNotFoundException("Department manager not found"));

}
}
