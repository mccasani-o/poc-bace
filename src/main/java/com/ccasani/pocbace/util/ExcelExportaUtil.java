package com.ccasani.pocbace.util;

import com.ccasani.pocbace.controller.GenericExcel;
import com.ccasani.pocbace.model.entity.UsuarioEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class ExcelExportaUtil {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<UsuarioEntity> usuarioEntities =new ArrayList<>();

    public ExcelExportaUtil(List<UsuarioEntity> usuarioEntities) {
        this.usuarioEntities = usuarioEntities;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Password", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
        cell.setCellStyle(GenericExcel.setCellStyle(workbook));
    }


    private void writeDataLines(List<UsuarioEntity> usuarioEntities) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (UsuarioEntity user : usuarioEntities) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getCorreo(), style);
            createCell(row, columnCount, user.getPassword(), style);
        }
    }

    public byte[] export(List<UsuarioEntity> usuarioEntities) throws IOException {
        writeHeaderLine();
        writeDataLines(usuarioEntities);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbook.write(stream);
        workbook.close();

        return stream.toByteArray();
    }
}
