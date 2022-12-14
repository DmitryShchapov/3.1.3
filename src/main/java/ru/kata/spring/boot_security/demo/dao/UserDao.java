package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    User getUser(long id);
    void deleteUserById(long id);
    User findUserByUsername(String username);
}
