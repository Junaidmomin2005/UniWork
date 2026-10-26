package com.example.UniWork.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "requirements")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequirementEntity {

    @Id
    private String id;

    // Who posted the requirement
    @DBRef
    private ProfileEntity postedByProfile;

    private String postedBy;

    // Basic details
    private String title;            // e.g. "Need Electrician for fan repair"
    private String description;      // detailed requirement
    private String category;         // e.g. "Electrician", "Plumbing", "Tutoring"
    private String location;         // optional if remote
    private boolean isRemote;        // true if work can be done online

    // Budget & time
    private Double budget;           // expected budget (optional)
    private String currency;         // e.g. INR, USD
    private String timeline;         // e.g. "2 days", "1 week"
    private LocalDate postedDate;
    private LocalDate deadline;      // optional deadline

    // Tracking
    private String status;           // OPEN, IN_PROGRESS, CLOSED

    // Providers who responded
    @DBRef
    private List<ProfileEntity> interestedProviders = new ArrayList<>();
}

