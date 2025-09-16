package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.location.Coordinates;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.CoordinatesDto;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {

        var packs = request.packages().stream()
                .map(this::createPack)
                .toList();

        var shipment = new Shipment(packs, currencyFactory.create(request.currencyCode()));

        var departure = createCoordinates(request.departure());
        var destination = createCoordinates(request.destination());

        var calculatedPrice = tariffCalculateUseCase.calc(shipment, departure, destination);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();

        return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
    }

    private Pack createPack(CargoPackage cargoPackage) {
        Weight weight = new Weight(cargoPackage.weight());
        Volume volume = new Volume(
                cargoPackage.length(),
                cargoPackage.width(),
                cargoPackage.height()
        ).normalize();

        return new Pack(weight, volume);
    }

    private Coordinates createCoordinates(CoordinatesDto dto) {
        return new Coordinates(dto.latitude(), dto.longitude());
    }
}