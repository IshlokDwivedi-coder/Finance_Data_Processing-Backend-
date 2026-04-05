package org.example.finance_backend.service;

import org.example.finance_backend.entity.User;
import org.example.finance_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user){

        // check if username already exists or not
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("Username already taken");
        }

        //encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //default status to ACTIVE
        if(user.getStatus()==null){
            user.setStatus(User.Status.ACTIVE);
        }

        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
