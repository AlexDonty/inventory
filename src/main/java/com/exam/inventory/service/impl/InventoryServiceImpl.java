package com.exam.inventory.service.impl;

import com.exam.inventory.common.constans.StatusEnum;
import com.exam.inventory.common.exception.ConflictException;
import com.exam.inventory.common.exception.NotFoundException;
import com.exam.inventory.dtos.ResponseDto;
import com.exam.inventory.dtos.TechnologicalAssetDto;
import com.exam.inventory.entity.CategoryEntity;
import com.exam.inventory.entity.StatusEntity;
import com.exam.inventory.entity.TechAssetsEntity;
import com.exam.inventory.repository.TechAssetsRepository;
import com.exam.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final TechAssetsRepository techAssetsRepository;

    @Override
    public ResponseDto createInventory(TechnologicalAssetDto techAssetDto) {

        TechAssetsEntity techAssetsEntity = new TechAssetsEntity();
        techAssetsEntity.setEntryDate(LocalDateTime.now());
        techAssetsEntity.setModel(techAssetDto.model());
        techAssetsEntity.setSerialNumber(techAssetDto.serialNumber());
        techAssetsEntity.setAcquisitionCost(techAssetDto.acquisitionCost());
        techAssetsEntity.setCategory(new CategoryEntity());
        techAssetsEntity.getCategory().setId(techAssetDto.categoryId());
        techAssetsEntity.setStatus(new StatusEntity());
        techAssetsEntity.getStatus().setId(techAssetDto.statusId());

        techAssetsRepository.save(techAssetsEntity);
        return new ResponseDto("Tech Asset created successfully");
    }

    @Override
    public ResponseDto updateInventory(String uuid, TechnologicalAssetDto techAssetDto) {

        final TechAssetsEntity techAssetsEntity = techAssetsRepository
                .findById(uuid).orElseThrow(()-> new NotFoundException("Tech Asset not found"));

        if (techAssetsEntity.getStatus().getStatus().equals(StatusEnum.REMOVED.name())) {
            throw new ConflictException("Tech Asset is already removed");
        }

        techAssetsEntity.setModel(techAssetDto.model());
        techAssetsEntity.setSerialNumber(techAssetDto.serialNumber());
        techAssetsEntity.setAcquisitionCost(techAssetDto.acquisitionCost());
        techAssetsEntity.getCategory().setId(techAssetDto.categoryId());
        techAssetsEntity.getStatus().setId(techAssetDto.statusId());

        techAssetsRepository.save(techAssetsEntity);

        return new ResponseDto( "Tech Asset updated successfully");
    }
}
