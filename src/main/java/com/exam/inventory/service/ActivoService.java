package com.exam.inventory.service;

import com.exam.inventory.dtos.ActivoDto;
import com.exam.inventory.dtos.ActivoFiltroDto;
import com.exam.inventory.dtos.FileDto;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ActivoService {
    Page<ActivoDto> consultar(ActivoFiltroDto filtro);

    FileDto generarFile(ActivoFiltroDto filtro) throws IOException;
}
