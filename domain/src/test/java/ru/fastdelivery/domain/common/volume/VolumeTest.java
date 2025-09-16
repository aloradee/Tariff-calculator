package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VolumeTest {

    @Test
    void whenCreateVolumeWithNegativeDimension_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Volume(BigDecimal.valueOf(-1), BigDecimal.valueOf(100), BigDecimal.valueOf(100))
        );
    }

    @Test
    void whenCreateVolumeWithTooLargeDimension_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Volume(BigDecimal.valueOf(1501), BigDecimal.valueOf(100), BigDecimal.valueOf(100))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {23, 47, 74, 99})
    void whenNormalize_thenRoundToMultipleOf50(int input) {
        Volume volume = new Volume(
                BigDecimal.valueOf(input),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(100)
        ).normalize();

        assertThat(volume.lengthMillimeters().intValue() % 50).isZero();
    }

    @Test
    void whenCalculateCubicMeters_thenReturnCorrectValue() {
        Volume volume = new Volume(
                BigDecimal.valueOf(350),
                BigDecimal.valueOf(600),
                BigDecimal.valueOf(250)
        );

        BigDecimal expected = BigDecimal.valueOf(0.0525);
        assertThat(volume.cubicMeters()).isEqualByComparingTo(expected);
    }
}