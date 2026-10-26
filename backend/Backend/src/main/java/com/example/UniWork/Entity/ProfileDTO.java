package com.example.UniWork.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ProfileDTO{

    @NotBlank
    private String name;

    @NotBlank
    private String skills;

    @NotNull
    private Long phoneNumber;

    @NotBlank
    private String location;

    @NotNull
    private Integer yearsOfExperience;

    private String bio;
}
