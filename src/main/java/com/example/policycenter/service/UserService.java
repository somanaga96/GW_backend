package com.example.policycenter.service;

import com.example.policycenter.model.User;
import com.example.policycenter.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}