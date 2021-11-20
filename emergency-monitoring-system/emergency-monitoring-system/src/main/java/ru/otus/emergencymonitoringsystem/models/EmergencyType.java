package ru.otus.emergencymonitoringsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "EmergencyTypes")
public class EmergencyType {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("identifier")
    private String identifier;
}
