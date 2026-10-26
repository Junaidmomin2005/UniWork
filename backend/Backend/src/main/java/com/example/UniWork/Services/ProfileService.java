package com.example.UniWork.Services;

import com.example.UniWork.Entity.*;
import com.example.UniWork.Repositories.LocationRepository;
import com.example.UniWork.Repositories.ProfileRepository;
import com.example.UniWork.Repositories.SkillsRepository;
import com.example.UniWork.Repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    LocationRepository locationRepository;

    public void saveProfile(ProfileDTO profileDTO, String userName){
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (userEntity == null){
            throw new RuntimeException("User Not Found");
        }
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(profileDTO.getName());
        profileEntity.setSkills(profileDTO.getSkills());
        profileEntity.setPhoneNumber(profileDTO.getPhoneNumber());
        profileEntity.setLocation(profileDTO.getLocation());
        profileEntity.setYearsOfExperience(profileDTO.getYearsOfExperience());
        profileEntity.setBio(profileDTO.getBio());

        profileRepository.save(profileEntity);
        userEntity.getProfile().add(profileEntity);
        userRepository.save(userEntity);

        SkillsDBEntity findSkills = skillsRepository.findBySkills(profileEntity.getSkills());
        if (findSkills == null){
            SkillsDBEntity skillsDBEntity = new SkillsDBEntity();
            skillsDBEntity.setSkills(profileEntity.getSkills());
            skillsRepository.save(skillsDBEntity);
        }

        LocationDBEntity findLocation = locationRepository.findByLocation(profileEntity.getLocation());
        if (findLocation == null){
            LocationDBEntity locationDBEntity = new LocationDBEntity();
            locationDBEntity.setLocation(profileEntity.getLocation());
            locationRepository.save(locationDBEntity);
        }

    }

    public ProfileEntity putUpdateProfile(ProfileDTO profileDTO,String userName){
        try {
            UserEntity userEntity = userRepository.findByUserName(userName); // Extracting User
            ProfileEntity profile = userEntity.getProfile().get(0);          // From user Extracting Profile

            profile.setName(profileDTO.getName());
            profile.setSkills(profileDTO.getSkills());
            profile.setPhoneNumber(profileDTO.getPhoneNumber());
            profile.setLocation(profileDTO.getLocation());
            profile.setYearsOfExperience(profileDTO.getYearsOfExperience());
            profile.setBio(profileDTO.getBio());

            profileRepository.save(profile);                               // Again Saving Updated Profile
            userEntity.getProfile().add(0,profile);
            return profile;
        }
        catch (Exception e){
            throw new RuntimeException("Profile Not Found "+e);
        }
    }

    public void removeProfile(String userName){
        try{
            UserEntity userEntity = userRepository.findByUserName(userName);
            ProfileEntity profile = userEntity.getProfile().get(0);

            userEntity.getProfile().remove(profile);

            profileRepository.deleteById(profile.getId());
            userRepository.save(userEntity);
        }
        catch (Exception e){
            throw new RuntimeException("Profile Not Found "+e );
        }
    }

    public ProfileEntity fetchProfileById(ObjectId id){
        return profileRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Profile Not Found"));
    }

    public ownerProfileDTO getMyProfile(String userName){
        ownerProfileDTO ownerProfile = new ownerProfileDTO();
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (!userEntity.getProfile().isEmpty()){
            ownerProfile.setName(userEntity.getProfile().get(0).getName());
            ownerProfile.setProfile(userEntity.getProfile().get(0));
            ownerProfile.setUserName(userEntity.getUserName());
        }
        else {
            ownerProfile.setName(userEntity.getName());
            ownerProfile.setUserName(userEntity.getUserName());
            ownerProfile.setProfile(null);
        }
        return ownerProfile;
    }

    public List<HomePageProfileDTO> getHomeProfileService(){
        List<ProfileEntity> allProfiles = profileRepository.findAll();
        return allProfiles.stream().map(profile -> {
            HomePageProfileDTO homePageProfileDTO = new HomePageProfileDTO();
            homePageProfileDTO.setId(profile.getId());
            homePageProfileDTO.setName(profile.getName());
            homePageProfileDTO.setSkills(profile.getSkills());
            homePageProfileDTO.setLocation(profile.getLocation());
            return homePageProfileDTO;
        }).collect(Collectors.toList());
    }

    public List<HomePageProfileDTO> getProfileBySkillFilter(String skills){
        List<ProfileEntity> allBySkills = profileRepository.findBySkillsContainingIgnoreCase(skills);
        return allBySkills.stream().map(profile -> {
            HomePageProfileDTO homePageProfileDTO = new HomePageProfileDTO();
            homePageProfileDTO.setId(profile.getId());
            homePageProfileDTO.setName(profile.getName());
            homePageProfileDTO.setSkills(profile.getSkills());
            homePageProfileDTO.setLocation(profile.getLocation());
            return homePageProfileDTO;
        }).collect(Collectors.toList());
    }

    public List<HomePageProfileDTO> getProfileByLocationFilter(String location){
        List<ProfileEntity> allByLocation = profileRepository.findByLocationContainingIgnoreCase(location);
        return allByLocation.stream().map(profile -> {
            HomePageProfileDTO homePageProfileDTO = new HomePageProfileDTO();
            homePageProfileDTO.setId(profile.getId());
            homePageProfileDTO.setName(profile.getName());
            homePageProfileDTO.setSkills(profile.getSkills());
            homePageProfileDTO.setLocation(profile.getLocation());
            return homePageProfileDTO;
        }).collect(Collectors.toList());
    }

    public List<HomePageProfileDTO> getProfileBySkillAndLocationFilter(String skills, String location){
        List<ProfileEntity> skillsAndLocationFilterProfiles;
        skillsAndLocationFilterProfiles = profileRepository.findBySkillsContainingIgnoreCaseAndLocationContainingIgnoreCase(skills, location);
        return skillsAndLocationFilterProfiles.stream().map(profile -> {
            HomePageProfileDTO homePageProfileDTO = new HomePageProfileDTO();
            homePageProfileDTO.setId(profile.getId());
            homePageProfileDTO.setName(profile.getName());
            homePageProfileDTO.setSkills(profile.getSkills());
            homePageProfileDTO.setLocation(profile.getLocation());
            return homePageProfileDTO;
        }).collect(Collectors.toList());
    }
}
