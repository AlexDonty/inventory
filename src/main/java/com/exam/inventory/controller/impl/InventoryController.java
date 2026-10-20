package com.exam.inventory.controller.impl;

import com.exam.inventory.controller.InventoryApi;
import com.exam.inventory.dtos.ResponseDto;
import com.exam.inventory.dtos.TechnologicalAssetDto;
import com.exam.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController implements InventoryApi {

    private final InventoryService inventoryService;

    @PostMapping( value = "/create", consumes = "application/json", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> createInventory(@Valid @RequestBody TechnologicalAssetDto techAssetDto) {
        return ResponseEntity.ok(inventoryService.createInventory(techAssetDto));
    }

    @PutMapping( value = "/update", consumes = "application/json", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> updateInventory(@RequestParam String uuid, @Valid @RequestBody TechnologicalAssetDto techAssetDto) {
        return ResponseEntity.ok(inventoryService.updateInventory(uuid, techAssetDto));
    }
}
