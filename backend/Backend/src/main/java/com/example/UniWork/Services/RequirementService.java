package com.example.UniWork.Services;

import com.example.UniWork.Entity.*;
import com.example.UniWork.Repositories.RequirementRepository;
import com.example.UniWork.Repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequirementService {

    @Autowired
    RequirementRepository requirementRepository;
    @Autowired
    UserRepository userRepository;

    public RequirementEntity saveRequirement(RequirementEntity requirement, String userName){
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (!userEntity.getProfile().isEmpty()){
            requirement.setPostedByProfile(userEntity.getProfile().get(0));
            requirement.setPostedBy(userEntity.getProfile().get(0).getName());
        }
        requirement.setPostedBy(userEntity.getName());
        requirement.setPostedDate(LocalDate.now());
        RequirementEntity savedEntity = requirementRepository.save(requirement);
        userEntity.getRequirements().add(savedEntity);
        userRepository.save(userEntity);
        return savedEntity;
    }

    public RequirementDTO getRequirementByIdNoAuth(ObjectId id){

            RequirementEntity requirementEntity = requirementRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Requirement Not Found !!! Check it Properly May Be Expired"));
            RequirementDTO requirementDTO = new RequirementDTO();
            requirementDTO.setPostedBy(requirementEntity.getPostedBy());
            requirementDTO.setTitle(requirementEntity.getTitle());
            requirementDTO.setDescription(requirementEntity.getDescription());
            requirementDTO.setCategory(requirementEntity.getCategory());
            requirementDTO.setLocation(requirementEntity.getLocation());
            requirementDTO.setRemote(requirementEntity.isRemote());

            requirementDTO.setBudget(requirementEntity.getBudget());
            requirementDTO.setCurrency(requirementEntity.getCurrency());
            requirementDTO.setTimeline(requirementEntity.getTimeline());
            requirementDTO.setPostedDate(requirementEntity.getPostedDate());
            requirementDTO.setDeadline(requirementEntity.getDeadline());
            requirementDTO.setStatus(requirementEntity.getStatus());

            if (requirementEntity.getPostedByProfile() == null){
                return requirementDTO;
            }
            requirementDTO.setProfileLink("http://localhost:8083/public/profile/"+requirementEntity.getPostedByProfile().getId());

            return requirementDTO;
    }

    public List<HomeRequirementsDTO> getHomePageRequirements(){
        List<RequirementEntity> allRequirements = requirementRepository.findAll();
        return allRequirements.stream().map(requirement -> {
            HomeRequirementsDTO homeRequirementsDTO = new HomeRequirementsDTO();

            // Convert ObjectId to String for frontend
            homeRequirementsDTO.setId(requirement.getId());
            homeRequirementsDTO.setTitle(requirement.getTitle());
            homeRequirementsDTO.setCategory(requirement.getCategory());
            homeRequirementsDTO.setLocation(requirement.getLocation());
            homeRequirementsDTO.setRemote(requirement.isRemote());
            homeRequirementsDTO.setDeadline(requirement.getDeadline());
            homeRequirementsDTO.setStatus(requirement.getStatus());
            return homeRequirementsDTO;
        }).collect(Collectors.toList());
    }

    public List<RequirementEntity> getOwnerRequiremnets(String userName){
        UserEntity user = userRepository.findByUserName(userName);
        List<RequirementEntity> requirements = user.getRequirements();
        if (requirements != null){
            return requirements;
        }
        throw new RuntimeException("Still No Requirements Added By U.....");
    }

    public RequirementEntity updateRequirements(RequirementEntity updatedRequirement,ObjectId id, String userName){
        UserEntity user = userRepository.findByUserName(userName);

        // Fetch existing requirement
        RequirementEntity existingRequirement = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));

        if (!user.getRequirements().contains(existingRequirement)) {
            throw new RuntimeException("You are not authorized to update this requirement");
        }

        // Update only the fields that are allowed to change
        existingRequirement.setTitle(updatedRequirement.getTitle());
        existingRequirement.setDescription(updatedRequirement.getDescription());
        existingRequirement.setCategory(updatedRequirement.getCategory());
        existingRequirement.setLocation(updatedRequirement.getLocation());
        existingRequirement.setBudget(updatedRequirement.getBudget());
        existingRequirement.setTimeline(updatedRequirement.getTimeline());
        existingRequirement.setDeadline(updatedRequirement.getDeadline());
        existingRequirement.setStatus(updatedRequirement.getStatus());

        // Save and return
        return requirementRepository.save(existingRequirement);
    }

    public boolean deleteRequirement(String userName, ObjectId id){
        UserEntity userEntity = userRepository.findByUserName(userName);
        RequirementEntity requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
        if (userEntity.getRequirements().contains(requirement)){
            requirementRepository.deleteById(id);
            userEntity.getRequirements().remove(requirement);
            userRepository.save(userEntity);
            return true;
        }
        else {
            throw new RuntimeException("Illegal Id found");
        }
    }

    public void applyForRequirements(String userName, ObjectId id){
        UserEntity applicantUserEntity = userRepository.findByUserName(userName);

        RequirementEntity requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));
        if (applicantUserEntity.getRequirements().contains(requirement)){
            throw new RuntimeException("Cannot Apply for own Requirements");
        }

        if (applicantUserEntity.getProfile() != null && !applicantUserEntity.getProfile().isEmpty()){
            ProfileEntity profile = applicantUserEntity.getProfile().get(0);
            if (requirement.getInterestedProviders().contains(profile)){
                throw new RuntimeException("Already Applied");
            }
            requirement.getInterestedProviders().add(profile);            // pushing to Requirements Collection or Table
            requirementRepository.save(requirement);
        }
        else {
            throw new RuntimeException("Please Create the Profile First");
        }
    }

    // Add this method to your RequirementService.java

    public List<ProfileEntity> getApplicantsByRequirementId(ObjectId requirementId, String userName) {
        UserEntity user = userRepository.findByUserName(userName);

        RequirementEntity requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));

        // Check if user owns this requirement
        if (!user.getRequirements().contains(requirement)) {
            throw new RuntimeException("You are not authorized to view applicants for this requirement");
        }

        return requirement.getInterestedProviders();
    }
}
