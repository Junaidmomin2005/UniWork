package com.example.UniWork.Entity;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class UserEntity {

    @Id
    private ObjectId id;

    private String name;

    @NonNull
    private String userName;

    private String password;

    @DBRef
    private List<ProfileEntity> profile = new ArrayList<>();

    @DBRef
    private List<RequirementEntity> requirements = new ArrayList<>();

    private List<String> roles;

}
