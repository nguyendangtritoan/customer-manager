package com.example.demo.domain;

import com.example.demo.user.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    User createCustomer(User user);
    List<User> listCustomer();

    boolean deleteUser(Integer id);

    Optional<User> updateUser(Integer id, User user);

}
