package com.example.UniWork.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequirementDTO {

    // Poster info
    private String postedBy; // only name/email/username shown
    private String profileLink; // link or id to fetch profile if exists

    // Basic details
    private String title;
    private String description;
    private String category;
    private String location;
    private boolean isRemote;

    // Budget & timeline
    private Double budget;
    private String currency;
    private String timeline;
    private LocalDate postedDate;
    private LocalDate deadline;

    // Tracking
    private String status;
}

