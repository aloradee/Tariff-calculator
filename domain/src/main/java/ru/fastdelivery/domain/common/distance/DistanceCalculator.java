package ru.fastdelivery.domain.common.distance;

import ru.fastdelivery.domain.common.location.Coordinates;

public class DistanceCalculator {
    private static final double EARTH_RADIUS_KM = 6371.0;

    public double calculateDistance(Coordinates point1, Coordinates point2) {
        double lat1 = Math.toRadians(point1.latitude());
        double lon1 = Math.toRadians(point1.longitude());
        double lat2 = Math.toRadians(point2.latitude());
        double lon2 = Math.toRadians(point2.longitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
