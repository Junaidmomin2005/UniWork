package com.example.UniWork.Controllers;

import com.example.UniWork.Entity.UserEntity;
import com.example.UniWork.Repositories.UserRepository;
import com.example.UniWork.Services.UserDetailServiceImplementaion;
import com.example.UniWork.Services.UserService;
import com.example.UniWork.Utils.JwtUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailServiceImplementaion userDetailServiceImplementaion;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserEntity user){
        try {
            UserEntity userEntity = userRepository.findByUserName(user.getUserName());
            if (userEntity != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with Email or UserName Already Exists");
            }
            userService.saveAuth(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(200).body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailServiceImplementaion.loadUserByUsername(user.getUserName());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("incorrect UserName or password"+e.getMessage());
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUser(@PathVariable ObjectId id){
        Optional<UserEntity> user = userService.findUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
