package com.exam.inventory.common.utils;
import com.exam.inventory.dtos.ActivoDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExportarExcel {

    public byte[] exportarExcel(Page<ActivoDto> activos) throws IOException {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Inventario");

            // Estilo del encabezado
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();

            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Encabezados
            String[] columnas = {
                    "ID",
                    "Folio inventario",
                    "Número de serie",
                    "Modelo",
                    "Estado",
                    "Costo adquisición",
                    "Fecha ingreso",
                    "Categoría"
            };

            Row header = sheet.createRow(0);

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // Formato moneda
            CellStyle moneyStyle = workbook.createCellStyle();
            moneyStyle.setDataFormat(
                    workbook.createDataFormat()
                            .getFormat("\"$\"#,##0.00")
            );

            // Formato fecha
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(
                    workbook.createDataFormat()
                            .getFormat("dd/mm/yyyy")
            );

            int rowNum = 1;

            // Llenar registros
            for (ActivoDto activo : activos.getContent()) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(
                        texto(activo.getId()));

                row.createCell(1).setCellValue(
                        texto(activo.getFolio()));

                row.createCell(2).setCellValue(
                        texto(activo.getSerie()));

                row.createCell(3).setCellValue(
                        texto(activo.getMarcaModelo()));

                row.createCell(4).setCellValue(
                        texto(activo.getEstado()));

                // Costo adquisición
                Cell costoCell = row.createCell(5);

                if (activo.getCostoAdquisicion() != null) {
                    costoCell.setCellValue(
                            activo.getCostoAdquisicion().doubleValue());
                    costoCell.setCellStyle(moneyStyle);
                }

                // Fecha ingreso
                Cell fechaCell = row.createCell(6);

                if (activo.getFechaIngreso() != null) {
                    fechaCell.setCellValue(
                            activo.getFechaIngreso());
                    fechaCell.setCellStyle(dateStyle);
                }

                row.createCell(7).setCellValue(
                        texto(activo.getCategoria()));


            }

            // Ajustar columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Generar archivo en memoria
            workbook.write(output);

            return output.toByteArray();
        }
    }

    private String texto(Object value) {
        return value == null ? "" : value.toString();
    }

    public String generarResumen(
            String usuario,
            int totalRegistros) {

        String fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy HH:mm:ss"
                ));

        return """
            REPORTE DE EXPORTACIÓN DE INVENTARIO
            ====================================

            Fecha y hora: %s
            Usuario solicitante: %s
            Total de registros exportados: %d
            """.formatted(fecha, usuario, totalRegistros);
    }
}
