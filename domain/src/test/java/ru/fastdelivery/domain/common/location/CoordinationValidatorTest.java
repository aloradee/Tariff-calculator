package ru.fastdelivery.domain.common.location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.fastdelivery.properties.provider.CoordinatesProperties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordinatesValidatorTest {

    private final CoordinatesProperties properties = new CoordinatesProperties();
    private final CoordinatesValidator validator = new CoordinatesValidator(properties);

    @ParameterizedTest
    @ValueSource(doubles = {44.9, 65.1})
    void whenLatitudeOutOfRange_thenThrowException(double latitude) {
        Coordinates coordinates = new Coordinates(latitude, 50.0);

        assertThrows(IllegalArgumentException.class, () ->
                validator.validate(coordinates)
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {29.9, 96.1})
    void whenLongitudeOutOfRange_thenThrowException(double longitude) {
        Coordinates coordinates = new Coordinates(55.0, longitude);

        assertThrows(IllegalArgumentException.class, () ->
                validator.validate(coordinates)
        );
    }

    @Test
    void whenCoordinatesWithinRange_thenNoException() {
        Coordinates coordinates = new Coordinates(55.0, 50.0);

        assertDoesNotThrow(() -> validator.validate(coordinates));
    }
}