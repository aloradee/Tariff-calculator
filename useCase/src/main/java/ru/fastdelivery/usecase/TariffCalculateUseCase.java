package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.distance.DistanceCalculator;
import ru.fastdelivery.domain.common.location.Coordinates;
import ru.fastdelivery.domain.common.location.CoordinatesValidator;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.properties.provider.CoordinatesProperties;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final CoordinatesProperties coordinatesProperties;
    private final DistanceCalculator distanceCalculator;
    private final CoordinatesValidator coordinatesValidator;

    public Price calc(Shipment shipment, Coordinates departure, Coordinates destination) {
        // Валидация координат
        coordinatesValidator.validate(departure);
        coordinatesValidator.validate(destination);

        Price basePrice = calculateBasePrice(shipment);

        // Расчет расстояния
        double distance = distanceCalculator.calculateDistance(departure, destination);

        return calculateFinalPrice(basePrice, distance);
    }
    private Price calculateBasePrice(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackages = shipment.volumeAllPackages();
        var minimalPrice = weightPriceProvider.minimalPrice();

        Price weightBasedPrice = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg);

        Price volumeBasedPrice = volumePriceProvider
                .costPerCubicMeter()
                .multiply(volumeAllPackages.cubicMeters());

        Price calculatedPrice = weightBasedPrice.max(volumeBasedPrice);

        return calculatedPrice.max(minimalPrice);
    }

    Price calculateFinalPrice(Price basePrice, double distance) {
        int threshold = coordinatesProperties.getDistanceThreshold();

        if (distance <= threshold) {
            return basePrice;
        }

        BigDecimal distanceFactor = BigDecimal.valueOf(distance)
                .divide(BigDecimal.valueOf(threshold), 10, RoundingMode.HALF_UP);

        BigDecimal finalAmount = basePrice.amount()
                .multiply(distanceFactor)
                .setScale(2, RoundingMode.UP); // Округление до копейки в большую сторону

        return new Price(finalAmount, basePrice.currency());
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
