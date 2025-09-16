package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления")
        @NotNull
        @NotEmpty
        List<@Valid CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,

        @Schema(description = "Координаты пункта назначения")
        @NotNull
        @Valid
        CoordinatesDto destination,

        @Schema(description = "Координаты пункта отправления")
        @NotNull
        @Valid
        CoordinatesDto departure
) {
}


