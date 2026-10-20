package com.exam.inventory.controller.impl;

import com.exam.inventory.controller.ReportApi;
import com.exam.inventory.dtos.ActivoDto;
import com.exam.inventory.dtos.ActivoFiltroDto;
import com.exam.inventory.dtos.FileDto;
import com.exam.inventory.dtos.PageResponseDto;
import com.exam.inventory.service.ActivoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController implements ReportApi {

    private final ActivoService activoService;




    @GetMapping
    public ResponseEntity<PageResponseDto<ActivoDto>> consultar(
            @Valid @ModelAttribute ActivoFiltroDto filter) {

        Page<ActivoDto> result = activoService.consultar(filter);

        return ResponseEntity.ok(
                PageResponseDto.from(result)
        );
    }

    @GetMapping("/file")
    public ResponseEntity<FileDto> consultarFile(
            @Valid @ModelAttribute ActivoFiltroDto filter) throws IOException {

        FileDto result = activoService.generarFile(filter);

        return ResponseEntity.ok(result);
    }
}
