package com.exam.inventory.service;

import com.exam.inventory.dtos.CategoryDto;
import com.exam.inventory.dtos.StatusDto;

import java.util.List;

public interface CatalogService {

    List<CategoryDto> getCatalogCategories();

    List<StatusDto> getCatalogInventoryStatus();
}
