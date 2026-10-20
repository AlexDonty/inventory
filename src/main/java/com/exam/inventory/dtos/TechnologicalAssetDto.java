package com.exam.inventory.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TechnologicalAssetDto(

        @NotBlank(message = "El modelo es obligatorio")
        String model,

        @NotBlank(message = "El número de serie es obligatorio")
        String serialNumber,

        @NotNull(message = "El costo es obligatorio")
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal acquisitionCost,

        @NotNull(message = "La categoría es obligatoria")
        Long categoryId,

        @NotNull(message = "El estado es obligatorio")
        Long statusId

) {}