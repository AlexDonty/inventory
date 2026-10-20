package com.exam.inventory.service;

import com.exam.inventory.dtos.ResponseDto;
import com.exam.inventory.dtos.TechnologicalAssetDto;

public interface InventoryService {

    ResponseDto createInventory(TechnologicalAssetDto techAssetDto);

    ResponseDto updateInventory(String uuid,TechnologicalAssetDto techAssetDto);
}
