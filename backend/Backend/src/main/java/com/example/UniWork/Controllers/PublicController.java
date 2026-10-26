package com.example.UniWork.Controllers;

import com.example.UniWork.Entity.HomePageProfileDTO;
import com.example.UniWork.Entity.HomeRequirementsDTO;
import com.example.UniWork.Entity.RequirementDTO;
import com.example.UniWork.Services.ProfileService;
import com.example.UniWork.Services.RequirementService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    ProfileService profileService;
    @Autowired
    RequirementService requirementService;

    // Requirement APIs
    @GetMapping("/requirement/{id}")
    public ResponseEntity<?> getRequirementById(@PathVariable ObjectId id){
        try {
            RequirementDTO requirement =
                    requirementService.getRequirementByIdNoAuth(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(requirement);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/requirements/home")
    public ResponseEntity<?> homePageRequirement(){
        try {
            List<HomeRequirementsDTO> homePageRequirements = requirementService.getHomePageRequirements();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(homePageRequirements);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(e.getMessage());
        }
    }

    // Profiles APIs
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable ObjectId id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(profileService.fetchProfileById(id));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/profiles/home")
    public ResponseEntity<?> homeProfile(){
        try{
            List<HomePageProfileDTO> homeProfileService = profileService.getHomeProfileService();
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(homeProfileService);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/profiles/filter/skills/{skills}")
    public ResponseEntity<?> skillFilterProfile(@PathVariable String skills){
        try {
            List<HomePageProfileDTO> profilesBySkillFilter = profileService.getProfileBySkillFilter(skills);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(profilesBySkillFilter);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/profiles/filter/location/{location}")
    public ResponseEntity<?> locationFilterProfile(@PathVariable String location){
        try {
            List<HomePageProfileDTO> profilesByLocationFilter = profileService.getProfileByLocationFilter(location);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(profilesByLocationFilter);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/profile/filter")
    public ResponseEntity<?> searchProfileByFilter(
            @RequestParam(required = false) String skills,
            @RequestParam(required = false) String location){
        try {
            List<HomePageProfileDTO> profiles;
            if (skills != null && location != null){
                profiles = profileService.getProfileBySkillAndLocationFilter(skills,location);
            }
            else if (skills != null) {
                profiles = profileService.getProfileBySkillFilter(skills);
            }
            else if (location != null) {
                profiles = profileService.getProfileByLocationFilter(location);
            }
            else {
                profiles = profileService.getHomeProfileService();
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(profiles);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}



