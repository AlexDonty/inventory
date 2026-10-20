package com.exam.inventory.service.impl;

import com.exam.inventory.dtos.CategoryDto;
import com.exam.inventory.dtos.StatusDto;
import com.exam.inventory.entity.CategoryEntity;
import com.exam.inventory.entity.StatusEntity;
import com.exam.inventory.repository.CategoryRepository;
import com.exam.inventory.repository.StatusRepository;
import com.exam.inventory.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CategoryRepository categoryRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<CategoryDto> getCatalogCategories() {

        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream().map(category -> new CategoryDto(
                        category.getId(),
                        category.getCategory(),
                        category.getCategoryCode()
                ))
                .toList();
    }

    @Override
    public List<StatusDto> getCatalogInventoryStatus() {
        List<StatusEntity> statuses = statusRepository.findAll();
        return statuses.stream().map(status -> new StatusDto(
                        status.getId(),
                        status.getStatus()
                ))
                .toList();
    }
}
