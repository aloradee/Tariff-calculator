package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(Weight weight, Volume volume) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final Volume maxVolume = new Volume(BigDecimal.valueOf(1500), BigDecimal.valueOf(1500), BigDecimal.valueOf(1500));

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package weight can't be more than " +
                    maxWeight.weightGrams() + " grams");
        }
        // Проверка объема выполняется в конструкторе Volume
    }
    public Pack(Weight weight) {
        this(weight, Volume.zero());
    }
}
