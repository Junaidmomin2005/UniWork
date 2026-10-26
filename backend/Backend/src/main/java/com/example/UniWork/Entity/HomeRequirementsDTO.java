package com.example.UniWork.Entity;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
public class HomeRequirementsDTO {
    private String id;  // Changed from ObjectId to String

    // Basic details
    private String title;

    private String category;
    private String location;
    private boolean isRemote;

    // Budget & timeline
    private LocalDate deadline;
    // Tracking
    private String status;
}