package com.example.UniWork.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "skills")
public class SkillsDBEntity {

    @Id
    private ObjectId id;

    private String skills;
}
