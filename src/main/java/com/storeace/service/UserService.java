package com.storeace.service;

import com.storeace.model.Users;
import com.storeace.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public void add(Users users) {
        userRepo.save(users);
    }

    public void update(Users users) {
        userRepo.save(users);
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public void delete(Users users) {
        userRepo.delete(users);
    }

    public Users find(Long id) {
        return userRepo.findUserById(id);
    }

    public List<Users> findAllByOrderByRole() {
        return userRepo.findAllByOrderByRole();
    }

    public Users findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<Users> findAll() {
        return userRepo.findAll();
    }
}
