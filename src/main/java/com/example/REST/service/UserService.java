package com.example.REST.service;

import com.example.REST.dto.UserFilter;
import com.example.REST.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll(UserFilter filter);
    User findById(Long id);
    User save(User user);
    User update(User user);
    void deleteById(Long id);
}
