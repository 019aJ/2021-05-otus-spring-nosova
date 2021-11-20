package ru.otus.weatherforecast.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForecastDTO {
    private Date time;
    private Double waterTemperature;
    //Direction of wind waves. 0° indicates waves coming from north
    private Double windDirection;
    // meters per second
    private Double windSpeed;
    private Double airTemperature;
}
