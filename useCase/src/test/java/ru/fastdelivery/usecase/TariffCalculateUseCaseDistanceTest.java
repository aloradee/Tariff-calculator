package ru.fastdelivery.usecase;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fastdelivery.domain.common.distance.DistanceCalculator;
import ru.fastdelivery.domain.common.location.CoordinatesValidator;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.properties.provider.CoordinatesProperties;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseDistanceTest {

    private final WeightPriceProvider weightPriceProvider = Mockito.mock(WeightPriceProvider.class);
    private final VolumePriceProvider volumePriceProvider = Mockito.mock(VolumePriceProvider.class);
    private final CoordinatesProperties properties = new CoordinatesProperties();
    private final DistanceCalculator distanceCalculator = Mockito.mock(DistanceCalculator.class);
    private final CoordinatesValidator coordinatesValidator = Mockito.mock(CoordinatesValidator.class);

    private final TariffCalculateUseCase useCase = new TariffCalculateUseCase(
            weightPriceProvider,
            volumePriceProvider,
            properties,
            distanceCalculator,
            coordinatesValidator
    );

    @Test
    void whenDistanceLessThanThreshold_thenReturnBasePrice() {
        when(weightPriceProvider.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(500), null));
        when(distanceCalculator.calculateDistance(any(), any())).thenReturn(200.0);

        Price basePrice = new Price(BigDecimal.valueOf(788.23), null);

        Price result = useCase.calculateFinalPrice(basePrice, 200.0);

        assertThat(result.amount()).isEqualByComparingTo("788.23");
    }

    @Test
    void whenDistanceGreaterThanThreshold_thenApplyDistanceFactor() {
        when(weightPriceProvider.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(500), null));

        Price basePrice = new Price(BigDecimal.valueOf(345.0), null);

        Price result = useCase.calculateFinalPrice(basePrice, 500.0);

        // (500/450) * 345 = 383.333... → округляем до 383.34
        assertThat(result.amount()).isEqualByComparingTo("383.34");
    }

    @Test
    void whenDistanceExactlyThreshold_thenReturnBasePrice() {
        when(weightPriceProvider.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(500), null));

        Price basePrice = new Price(BigDecimal.valueOf(600.0), null);

        Price result = useCase.calculateFinalPrice(basePrice, 450.0);

        assertThat(result.amount()).isEqualByComparingTo("600.00");
    }
}