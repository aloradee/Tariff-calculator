package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Координаты местоположения")
public record CoordinatesDto(
        @Schema(description = "Широта", example = "55.027532")
        @NotNull
        @DecimalMin(value = "-90.0", message = "Latitude must be at least -90")
        @DecimalMax(value = "90.0", message = "Latitude must be at most 90")
        Double latitude,

        @Schema(description = "Долгота", example = "73.398660")
        @NotNull
        @DecimalMin(value = "-180.0", message = "Longitude must be at least -180")
        @DecimalMax(value = "180.0", message = "Longitude must be at most 180")
        Double longitude
) {
}