package ru.otus.emergencymonitoringsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterAreaDTO {
    private String id;
    private String name;
    private String identifier;
    private String coordinates;
}
