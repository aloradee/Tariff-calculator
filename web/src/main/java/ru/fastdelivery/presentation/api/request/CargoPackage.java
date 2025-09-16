package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667")
        @NotNull
        @DecimalMin(value = "0", message = "Weight cannot be negative")
        BigInteger weight,

        @Schema(description = "Длина упаковки, мм", example = "345")
        @DecimalMin(value = "0", message = "Length cannot be negative")
        BigDecimal length,

        @Schema(description = "Ширина упаковки, мм", example = "589")
        @DecimalMin(value = "0", message = "Width cannot be negative")
        BigDecimal width,

        @Schema(description = "Высота упаковки, мм", example = "234")
        @DecimalMin(value = "0", message = "Height cannot be negative")
        BigDecimal height
) {
        public CargoPackage(BigInteger weight) {
                this(weight, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }
}
