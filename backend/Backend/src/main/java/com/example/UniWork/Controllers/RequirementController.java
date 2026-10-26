package com.example.UniWork.Controllers;

import com.example.UniWork.Entity.ProfileEntity;
import com.example.UniWork.Entity.RequirementEntity;
import com.example.UniWork.Services.RequirementService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requirement")
public class RequirementController {

    @Autowired
    RequirementService requirementService;

    @PostMapping("/post-requirement")
    public ResponseEntity<?> postRequirement(@RequestBody RequirementEntity requirement){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            RequirementEntity savedRequirementEntity = requirementService.saveRequirement(requirement, userName);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(savedRequirementEntity);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getStackTrace());
        }
    }
    @GetMapping("/owner")
    public ResponseEntity<?> ownerProfile(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            List<RequirementEntity> ownerRequiremnets = requirementService.getOwnerRequiremnets(userName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ownerRequiremnets);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getStackTrace());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRequirements(@RequestBody RequirementEntity requirement,@PathVariable ObjectId id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            RequirementEntity updatedRequirementEntity = requirementService.updateRequirements(requirement, id, userName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedRequirementEntity);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRequirement(@PathVariable ObjectId id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean deleted = requirementService.deleteRequirement(userName,id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(deleted);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/apply/{id}")
    public ResponseEntity<?> applyButton(@PathVariable ObjectId id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            requirementService.applyForRequirements(userName,id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Applied For the Requirements");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Add this endpoint to your RequirementController.java

    @GetMapping("/applicants/{id}")
    public ResponseEntity<?> getApplicants(@PathVariable ObjectId id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            List<ProfileEntity> applicants = requirementService.getApplicantsByRequirementId(id, userName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(applicants);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
