package com.exam.inventory.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivoDto {

    private String id;

    private String folio;

    private String serie;

    private String marcaModelo;

    private Long estadoId;

    private String estado;

    private BigDecimal costoAdquisicion;

    private LocalDateTime fechaIngreso;

    private Long categoriaId;

    private String categoria;
}