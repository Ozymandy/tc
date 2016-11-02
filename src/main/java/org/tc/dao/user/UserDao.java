package org.tc.dao.user;

import org.tc.models.User;

import java.util.List;

public interface UserDao {
    void create(User newUser);

    List<User> getAll();

    User getByName(String username);

    void update(User changedUser);
}
