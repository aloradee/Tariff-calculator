package ru.fastdelivery.domain.common.distance;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.location.Coordinates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class DistanceCalculatorTest {

    private final DistanceCalculator calculator = new DistanceCalculator();

    @Test
    void whenCalculateDistanceBetweenSamePoints_thenReturnZero() {
        Coordinates point1 = new Coordinates(55.7558, 37.6173);
        Coordinates point2 = new Coordinates(55.7558, 37.6173);

        double distance = calculator.calculateDistance(point1, point2);

        assertThat(distance).isEqualTo(0.0);
    }

    @Test
    void whenCalculateDistanceBetweenMoscowAndSPb_thenReturnCorrectValue() {
        // Москва - Санкт-Петербург ≈ 634 км
        Coordinates moscow = new Coordinates(55.7558, 37.6173);
        Coordinates stPetersburg = new Coordinates(59.9343, 30.3351);

        double distance = calculator.calculateDistance(moscow, stPetersburg);

        assertThat(distance).isCloseTo(634.0, within(10.0));
    }

    @Test
    void whenCalculateDistanceWithTestDataFromArticle_thenReturnCorrectValue() {
        // Тестовые данные из статьи про формулу гаверсинусов
        Coordinates point1 = new Coordinates(52.2296756, 21.0122287);
        Coordinates point2 = new Coordinates(41.89193, 12.51133);

        double distance = calculator.calculateDistance(point1, point2);

        // Ожидаемое расстояние ~1315 км
        assertThat(distance).isCloseTo(1315.0, within(10.0));
    }
}