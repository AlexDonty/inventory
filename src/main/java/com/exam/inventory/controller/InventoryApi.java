package com.exam.inventory.controller;

import com.exam.inventory.dtos.ResponseDto;
import com.exam.inventory.dtos.TechnologicalAssetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Inventario", description = "Gestión de activos tecnológicos")
public interface InventoryApi {

    @Operation(
            summary = "Alta de Activos tecnológicos",
            description = "Permite dar de alta un activo tecnológico en el inventario"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Activo tecnológico creado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    ResponseEntity<ResponseDto> createInventory(TechnologicalAssetDto techAssetDto);

    @Operation(
            summary = "Actualización de Activos tecnológicos",
            description = "Permite actualizar el estatus tecnológico en el inventario"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actualización exitosa de activos tecnológicos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Activo tecnológico no encontrado"
            )
    })
    ResponseEntity<ResponseDto> updateInventory(String uuid, TechnologicalAssetDto techAssetDto);




}
