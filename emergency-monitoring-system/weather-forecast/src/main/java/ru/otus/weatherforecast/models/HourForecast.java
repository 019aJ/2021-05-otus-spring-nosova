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
public class HourForecast {
    private Date time;
    private Temperature waterTemperature;
    private Wind windDirection;
    private Wind windSpeed;
    private Temperature airTemperature;
}
