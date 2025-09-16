package ru.fastdelivery.domain.common.location;

public record Coordinates(double latitude, double longitude) {
    public Coordinates {
        validateCoordinates(latitude, longitude);
    }

    private void validateCoordinates(double latitude, double longitude) {
        if(latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
        }
        if(longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
        }
    }
}
