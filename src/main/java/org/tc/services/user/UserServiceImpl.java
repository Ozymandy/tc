package org.tc.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tc.dao.user.UserDao;
import org.tc.models.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDao userDao;

    @Override
    public void create(User newUser) {
        String password=bCryptPasswordEncoder
                .encode(newUser.getPassword());
        newUser.setPassword(password);
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
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        return userDao.getByName(auth.getName());
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
