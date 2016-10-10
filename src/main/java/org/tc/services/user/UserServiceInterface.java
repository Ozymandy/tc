package org.tc.services.user;

import org.tc.models.User;

import java.util.List;

public interface UserServiceInterface {
    void create(User newUser);

    void delete(User user);

    List<User> getAll();

    User getById(int id);

    User getByName(String username);

    void update(User changedUser);

}
