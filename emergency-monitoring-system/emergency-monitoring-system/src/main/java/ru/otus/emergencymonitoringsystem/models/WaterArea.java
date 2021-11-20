package ru.otus.emergencymonitoringsystem.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Areas")
public class WaterArea {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("identifier")
    private String identifier;

    @Field("coordinates")
    private Polygon coordinates;
}
