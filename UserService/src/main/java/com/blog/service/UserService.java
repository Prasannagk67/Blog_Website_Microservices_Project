package com.blog.service;

import com.blog.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User creatUser(User user);
    List<User> getAllUser();
    Optional<User> getUserById(int id);
    User updateUser(int id, User user);
    void deleteUserById(int id);
}
