package com.exam.inventory.controller;

import com.exam.inventory.dtos.CategoryDto;
import com.exam.inventory.dtos.StatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Catalog", description = "API para la gestión del catálogo de inventario")
public interface CatalogApi {

    @Operation(
        summary = "Obtener catálogo de categorías",
        description = "Obtiene el catálogo de inventario"
    )
    ResponseEntity<List<CategoryDto>> getCatalogCategories();

    @Operation(
        summary = "Obtener catálogo de estado de inventario",
        description = "Obtiene el catálogo de estado de inventario"
    )
    ResponseEntity<List<StatusDto>> getCatalogInventoryStatus();

}
