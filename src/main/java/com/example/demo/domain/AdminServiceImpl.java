package com.example.demo.domain;

import com.example.demo.user.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;


    @Override
    public User createCustomer(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> listCustomer() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Optional<User> updateUser(Integer id, User updateUser) {
        return Optional.ofNullable(userRepository.update(id, updateUser));
    }
}
