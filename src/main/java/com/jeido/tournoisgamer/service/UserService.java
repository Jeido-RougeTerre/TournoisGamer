package com.jeido.tournoisgamer.service;


import com.jeido.tournoisgamer.entity.User;
import com.jeido.tournoisgamer.repository.UserRepository;
import com.jeido.tournoisgamer.utils.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByName(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> search(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public List<User> getAdmins() {
        return userRepository.findByRole(Role.ADMIN);
    }

    public void delete(String name) {
        userRepository.deleteByName(name);
    }

    public boolean exist(String nameOrEmail) {
        return userRepository.existsByName(nameOrEmail) || userRepository.existsByEmail(nameOrEmail);
    }

}
