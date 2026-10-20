package com.exam.inventory.controller;

import com.exam.inventory.dtos.ActivoDto;
import com.exam.inventory.dtos.ActivoFiltroDto;
import com.exam.inventory.dtos.PageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Inventario", description = "Consulta de activos tecnológicos en el inventario")
public interface ReportApi {

    @Operation(
            summary = "Consulta de activos tecnológicos",
            description = "Permite consultar activos tecnológicos según filtros específicos"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa de activos tecnológicos"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Acceso no autorizado"
            )
    })
    ResponseEntity<PageResponseDto<ActivoDto>> consultar(ActivoFiltroDto filter);
}
