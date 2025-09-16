package ru.fastdelivery.properties.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("coordinates")
@Setter
@Getter
public class CoordinatesProperties {
    private double minLatitude = 45.0;
    private double maxLatitude = 65.0;
    private double minLongitude = 30.0;
    private double maxLongitude = 96.0;
    private int distanceThreshold = 450;

}
