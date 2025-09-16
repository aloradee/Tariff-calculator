package ru.fastdelivery.domain.common.volume;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Volume(
        BigDecimal lengthMillimeters,
        BigDecimal widthMillimeters,
        BigDecimal heightMillimeters) {

    public Volume {
        if (isLessThanZero(lengthMillimeters) || isLessThanZero(widthMillimeters) || isLessThanZero(heightMillimeters)) {
            throw new IllegalArgumentException("Volume dimensions cannot be below Zero!");
        }
        if (isGreaterThanMax(lengthMillimeters) || isGreaterThanMax(widthMillimeters) || isGreaterThanMax(heightMillimeters)) {
            throw new IllegalArgumentException("Volume dimensions cannot be more than 1500 mm!");
        }
    }

    private static boolean isLessThanZero(BigDecimal value) {
        return BigDecimal.ZERO.compareTo(value) > 0;
    }

    private static boolean isGreaterThanMax(BigDecimal value) {
        return value.compareTo(BigDecimal.valueOf(1500)) > 0;
    }

    public Volume normalize() {
        BigDecimal normalizedLength = roundToMultipleOf50(lengthMillimeters);
        BigDecimal normalizedWidth = roundToMultipleOf50(widthMillimeters);
        BigDecimal normalizedHeight = roundToMultipleOf50(heightMillimeters);

        return new Volume(normalizedLength, normalizedWidth, normalizedHeight);
    }


    public BigDecimal roundToMultipleOf50(BigDecimal value) {
        BigDecimal divided = value.divide(BigDecimal.valueOf(50), 0, RoundingMode.HALF_UP);
        return divided.multiply(BigDecimal.valueOf(50));
    }

    public BigDecimal cubicMeters() {
        BigDecimal volumeMm = lengthMillimeters
                .multiply(widthMillimeters)
                .multiply(heightMillimeters);

        return volumeMm.divide(BigDecimal.valueOf(1_000_000_000), 4, RoundingMode.HALF_UP);
    }

    public static Volume zero() {
        return new Volume(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public Volume add(Volume additionalVolume) {
        BigDecimal totalCubicMeters = this.cubicMeters().add(additionalVolume.cubicMeters());

        BigDecimal sideLength = BigDecimal.valueOf(Math.cbrt(totalCubicMeters.doubleValue()))
                .multiply(BigDecimal.valueOf(1000));

        return new Volume(sideLength, sideLength, sideLength);
    }
}
