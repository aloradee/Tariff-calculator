package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;

    public Price calc(Shipment shipment) {
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

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
