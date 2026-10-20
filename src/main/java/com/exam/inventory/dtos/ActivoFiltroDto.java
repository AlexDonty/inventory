package com.exam.inventory.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ActivoFiltroDto {

    private String serie;
    private String marcaModelo;

    private Long categoriaId;
    private Long estadoId;

    private BigDecimal costoMin;
    private BigDecimal costoMax;

    private Integer page = 0;
    private Integer size = 10;

    private String sortBy = "id";
    private String direction = "asc";
}