package com.exam.inventory.service.impl;

import com.exam.inventory.common.utils.ExportarExcel;
import com.exam.inventory.common.utils.TechAssetsSpecification;
import com.exam.inventory.dtos.ActivoDto;
import com.exam.inventory.dtos.ActivoFiltroDto;
import com.exam.inventory.dtos.FileDto;
import com.exam.inventory.entity.TechAssetsEntity;
import com.exam.inventory.repository.TechAssetsRepository;
import com.exam.inventory.service.ActivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class ActivoServiceImpl implements ActivoService {

    private final TechAssetsRepository techAssetsRepository;

    private static final Map<String, String> CAMPOS_ORDEN =
            Map.of(
                    "id", "id",
                    "folio", "folio",
                    "serie", "serialNumber",
                    "marcaModelo", "model",
                    "costoAdquisicion", "acquisitionCost",
                    "fechaIngreso", "entryDate"
            );

    @Override
    @Transactional(readOnly = true)
    public Page<ActivoDto> consultar(ActivoFiltroDto filtro) {

        // 1. Validar paginación
        int page = filtro.getPage() == null
                ? 0 : filtro.getPage();

        int size = filtro.getSize() == null
                ? 10 : filtro.getSize();

        if (page < 0) {
                throw new IllegalArgumentException(
                        "La página no puede ser negativa"
                );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "El tamaño debe estar entre 1 y 100"
            );
        }

        // 2. Validar rango de costos
        BigDecimal costoMin = filtro.getCostoMin();
        BigDecimal costoMax = filtro.getCostoMax();

        if ((costoMin != null && costoMin.signum() < 0)
                || (costoMax != null && costoMax.signum() < 0)) {
            throw new IllegalArgumentException(
                    "Los costos no pueden ser negativos"
            );
        }

        if (costoMin != null && costoMax != null
                && costoMin.compareTo(costoMax) > 0) {

            throw new IllegalArgumentException(
                    "El costo mínimo no puede superar al máximo"
            );
        }

        // 3. Validar campo de ordenamiento
        String sortBy = filtro.getSortBy() == null
                ? "id" : filtro.getSortBy();

        String campo = CAMPOS_ORDEN.get(sortBy);

        if (campo == null) {
            throw new IllegalArgumentException(
                    "Campo de ordenamiento no permitido: " + sortBy
            );
        }

        String direction = filtro.getDirection() == null
                ? "asc" : filtro.getDirection();

        Sort.Direction direccion;

        try {
            direccion = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "La dirección debe ser asc o desc"
            );
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direccion, campo)
        );

        Specification<TechAssetsEntity> specification =
                TechAssetsSpecification.filtrar(filtro);

        Page<TechAssetsEntity> resultado =
                techAssetsRepository.findAll(
                        specification,
                        pageable
                );


        return resultado.map(this::convertirDto);
    }

    @Override
    public FileDto generarFile(ActivoFiltroDto filtro) throws IOException {

        Page<ActivoDto> con = consultar(filtro);
        ExportarExcel exportarExcel = new ExportarExcel();
        byte[] excelBytes = exportarExcel.exportarExcel(con);

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String usuario = authentication.getName();

        String resumen = exportarExcel.generarResumen(
                usuario,
                con.getNumberOfElements()
        );

        byte[] zipBytes = compressExcel(excelBytes, resumen);

        // 3. Convertir ZIP a Base64
        String base64 = Base64.getEncoder()
                .encodeToString(zipBytes);

        return new FileDto(
                200,
                "Reporte generado correctamente",
                "inventario.zip",
                base64
        );
    }

    public byte[] compressExcel(byte[] excelBytes, String resumen)
            throws IOException {

        try (ByteArrayOutputStream output =
                     new ByteArrayOutputStream();
             ZipOutputStream zip =
                     new ZipOutputStream(output)) {

            ZipEntry entry = new ZipEntry("inventario.xlsx");

            zip.putNextEntry(entry);
            zip.write(excelBytes);
            zip.closeEntry();

            zip.putNextEntry(new ZipEntry("resumen.txt"));
            zip.write(resumen.getBytes(StandardCharsets.UTF_8));
            zip.closeEntry();

            zip.finish();

            return output.toByteArray();
        }
    }

    private ActivoDto convertirDto(TechAssetsEntity entity) {

        return ActivoDto.builder()
                .id(entity.getId())
                .folio(entity.getFolio())
                .serie(entity.getSerialNumber())
                .marcaModelo(entity.getModel())
                .estadoId(
                        entity.getStatus().getId()
                )
                .estado(
                        entity.getStatus().getStatus()
                )
                .costoAdquisicion(
                        entity.getAcquisitionCost()
                )
                .fechaIngreso(
                        entity.getEntryDate()
                )
                .categoriaId(
                        entity.getCategory().getId()
                )
                .categoria(
                        entity.getCategory().getCategory()
                )
                .build();
    }
}