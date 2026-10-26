package com.example.UniWork.Controllers;

import com.example.UniWork.Entity.ProfileDTO;
import com.example.UniWork.Entity.ProfileEntity;
import com.example.UniWork.Entity.UserEntity;
import com.example.UniWork.Entity.ownerProfileDTO;
import com.example.UniWork.Repositories.UserRepository;
import com.example.UniWork.Services.ProfileService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.lang.ref.PhantomReference;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    @Autowired
    ProfileService profileService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create-profile")
    public ResponseEntity<?> pushProfile(@Valid @RequestBody ProfileDTO profileDTO, BindingResult result){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UserEntity userEntity = userRepository.findByUserName(userName);
            if (!userEntity.getProfile().isEmpty()){
                throw new RuntimeException("Profile Already Exists if need go and Edit");
            }
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Fields Cannot be empty"+result.getAllErrors());
            }

            profileService.saveProfile(profileDTO,userName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(profileDTO);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ProfileDTO profileDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            ProfileEntity updatedProfile = profileService.putUpdateProfile(profileDTO, userName);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(updatedProfile);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteProfile(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            profileService.removeProfile(userName);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(true);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getOwnerProfile(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            ownerProfileDTO ownerProfile = profileService.getMyProfile(userName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ownerProfile);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(e.getMessage());
        }

    }
}
