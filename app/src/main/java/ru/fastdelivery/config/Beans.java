package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.domain.common.distance.DistanceCalculator;
import ru.fastdelivery.domain.common.location.CoordinatesValidator;
import ru.fastdelivery.properties.provider.CoordinatesProperties;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public DistanceCalculator distanceCalculator() {
        return new DistanceCalculator();
    }

    @Bean
    public CoordinatesValidator coordinatesValidator(CoordinatesProperties coordinatesProperties) {
        return new CoordinatesValidator(coordinatesProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(
            WeightPriceProvider weightPriceProvider,
            VolumePriceProvider volumePriceProvider,
            CoordinatesProperties coordinatesProperties,
            DistanceCalculator distanceCalculator,
            CoordinatesValidator coordinatesValidator) {
        return new TariffCalculateUseCase(
                weightPriceProvider,
                volumePriceProvider,
                coordinatesProperties,
                distanceCalculator,
                coordinatesValidator
        );
    }
}
