package ru.fastdelivery.domain.common.location;

import ru.fastdelivery.properties.provider.CoordinatesProperties;

public class CoordinatesValidator {

    private final CoordinatesProperties properties;

    public CoordinatesValidator(CoordinatesProperties properties) {
        this.properties = properties;
    }

    public void validate(Coordinates coordinates) {
        validateLatitude(coordinates.latitude());
        validateLongitude(coordinates.longitude());
    }

    private void validateLatitude(double latitude) {
        if (latitude < properties.getMinLatitude() || latitude > properties.getMaxLatitude()) {
            throw new IllegalArgumentException(
                    String.format("Latitude must be between %.1f and %.1f degrees",
                            properties.getMinLatitude(), properties.getMaxLatitude())
            );
        }
    }

    private void validateLongitude(double longitude) {
        if (longitude < properties.getMinLongitude() || longitude > properties.getMaxLongitude()) {
            throw new IllegalArgumentException(
                    String.format("Longitude must be between %.1f and %.1f degrees",
                            properties.getMinLongitude(), properties.getMaxLongitude())
            );
        }
    }
}
