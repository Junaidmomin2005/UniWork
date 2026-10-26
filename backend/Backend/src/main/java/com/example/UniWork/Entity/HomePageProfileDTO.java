package com.example.UniWork.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public class HomePageProfileDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String name;

    private String skills;

    private String location;
}
