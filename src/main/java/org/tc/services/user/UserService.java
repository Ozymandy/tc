package org.tc.services.user;

import org.tc.models.User;

import java.util.List;

public interface UserService {
    void create(User newUser);

    void delete(User user);

    List<User> getAll();

    User getById(int id);

    User getCurrentUser();

    User getByName(String username);

    void update(User changedUser);
}
