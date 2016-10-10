package org.tc.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tc.dao.user.UserDaoInterface;
import org.tc.models.User;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDaoInterface userDao;

    @Override
    public void create(User newUser) {
        userDao.create(newUser);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public User getByName(String username) {
        return userDao.getByName(username);
    }

    @Override
    public void update(User changedUser) {
        changedUser.setPassword(bCryptPasswordEncoder
                .encode(changedUser.getPassword()));
        userDao.update(changedUser);
    }
}
