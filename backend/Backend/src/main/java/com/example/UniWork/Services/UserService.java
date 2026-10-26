package com.example.UniWork.Services;

import com.example.UniWork.Entity.UserEntity;
import com.example.UniWork.Repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    public boolean save(UserEntity user){
            userRepository.save(user);
            return true;
    }

    public void saveAuth(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList("USER"));
        userRepository.save(user);
    }

    public Optional<UserEntity> findUser(ObjectId id){
        return userRepository.findById(id);
    }

}
