package org.tc.dao.user;

import org.tc.models.User;

import java.util.List;

public interface UserDao {
    void create(User newUser);

    void delete(User user);

    List<User> getAll();

    User getById(int id);

    User getByEmail(String email);

    User getByName(String username);

    void update(User changedUser);
}
