package com.exam.inventory.controller.impl;

import com.exam.inventory.controller.CatalogApi;
import com.exam.inventory.dtos.CategoryDto;
import com.exam.inventory.dtos.StatusDto;
import com.exam.inventory.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController implements CatalogApi {

    private final CatalogService catalogService;

    @GetMapping(value = "/categories",produces = "application/json")
    @Override
    public ResponseEntity<List<CategoryDto>> getCatalogCategories() {
        return ResponseEntity.ok(catalogService.getCatalogCategories());
    }

    @GetMapping(value = "/status", produces = "application/json")
    @Override
    public ResponseEntity<List<StatusDto>> getCatalogInventoryStatus() {
        return ResponseEntity.ok(catalogService.getCatalogInventoryStatus());
    }
}
