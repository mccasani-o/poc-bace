package com.ccasani.pocbace.service;

import com.ccasani.pocbace.common.MessageComponent;
import com.ccasani.pocbace.controller.GenericExcel;
import com.ccasani.pocbace.model.CustomException;
import com.ccasani.pocbace.model.ErrorTypeEnum;
import com.ccasani.pocbace.model.MessageKey;
import com.ccasani.pocbace.model.UsuarioEvent;
import com.ccasani.pocbace.model.entity.UsuarioEntity;
import com.ccasani.pocbace.model.request.UsuarioRequest;
import com.ccasani.pocbace.model.response.UsuarioResponse;
import com.ccasani.pocbace.repository.UsuarioRepository;
import com.ccasani.pocbace.security.model.RolNombre;
import com.ccasani.pocbace.util.ExcelExportaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ExcelExportaUtil excelExportaUtil;
    private final MessageComponent messageComponent;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;


    @Override
    public byte[] exportar() throws IOException {
        List<UsuarioEntity> usuarioEntities = this.usuarioRepository.findAll();
        return this.excelExportaUtil.export(usuarioEntities);
    }

    @Override
    public byte[] exportData() throws IOException {

        XSSFWorkbook workbookNuevo = new XSSFWorkbook();
        XSSFSheet sheetNuevo = workbookNuevo.createSheet("Actividad SL Beca");
        Row row = sheetNuevo.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(GenericExcel.setCellStyle(workbookNuevo));

        cell = row.createCell(1);
        cell.setCellValue("EMEAL");
        cell.setCellStyle(GenericExcel.setCellStyle(workbookNuevo));

        cell = row.createCell(2);
        cell.setCellValue("PASSWORD");
        cell.setCellStyle(GenericExcel.setCellStyle(workbookNuevo));

        List<UsuarioEntity> usuarioEntities = this.usuarioRepository.findAll();
        IntStream.range(1, usuarioEntities.size()).forEach(i -> {

            Row rowData = sheetNuevo.createRow(i);

            UsuarioEntity aSLB = usuarioEntities.get(i);
            if (isNull(aSLB)) {
                return;
            }
            Cell cellData = rowData.createCell(0);
            cellData.setCellValue(aSLB.getId());

            cellData = rowData.createCell(1);
            cellData.setCellValue(aSLB.getCorreo());

            cellData = rowData.createCell(2);
            cellData.setCellValue(aSLB.getPassword());

        });


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbookNuevo.write(stream);
        workbookNuevo.close();

        return stream.toByteArray();
    }

    @Override
    public byte[] exportData2() throws IOException {
        final List<String> columnNameList = List.of("Id", "Usuario", "Email", "Password", "Rol");
        final var outputStream = new ByteArrayOutputStream();
        final var usuarios = this.listarUsuarios();
        final var workBook = new XSSFWorkbook();
        final var workSheet = workBook.createSheet("Employee Records");
        final var initialRow = workSheet.createRow(0);

        // To make heading row's font bold
        final var style = workBook.createCellStyle();
        final var font = workBook.createFont();
        font.setBold(true);
        style.setFont(font);

        // Writing heading cell names in inital-row
        for (int column = 0; column < columnNameList.size(); column++) {
            final var cell = initialRow.createCell(column);
            cell.setCellStyle(style);
            cell.setCellValue(columnNameList.get(column));
        }



        // Iterating over each employee
        for (int row = 0; row < usuarios.size(); row++) {

            // making a new row representing the current employee
            final var currentRow = workSheet.createRow(row + 1);

            // populating current employees data in columns
            for (int column = 0; column < columnNameList.size(); column++) {
                final var currentCell = currentRow.createCell(column);
                final var usuarioResponse = usuarios.get(row);
                currentCell.setCellValue(this.excelExportaUtil.getValue(column, usuarioResponse));
            }

        }

        // auto sizing each column in worksheet
        for (int column = 0; column < columnNameList.size(); column++) {
            workSheet.autoSizeColumn(column);
        }

        workBook.write(outputStream);
        workBook.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

    @Transactional
    @Override
    public void saveUsuario(UsuarioRequest usuarioRequest) {
        if (this.existsByCorreo(usuarioRequest.getCorreo())) {
            throw new CustomException(ErrorTypeEnum.CODE_BAD_REQUEST.getCode(),
                    this.messageComponent.getMessagesKey(MessageKey.RESPONSE_CORREO_ERROR),
                    HttpStatus.BAD_REQUEST);
        }
        UsuarioEntity usuarioEntity = this.usuarioRepository.save(this.toUsuarioEntity(usuarioRequest));
        if (usuarioEntity.getNombreUsuario() == null) {
            throw new CustomException(ErrorTypeEnum.CODE_BAD_REQUEST.getCode(), "Error transaccion", HttpStatus.BAD_REQUEST);
        }
        this.eventPublisher.publishEvent(new UsuarioEvent(usuarioEntity));

    }

    @Override
    public List<UsuarioResponse> listarUsuarios() {


        return this.usuarioRepository.findAll()
                .stream()
                .map(this::toUsuario)
                .toList();
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return this.usuarioRepository.existsByCorreo(correo);
    }

    private UsuarioEntity toUsuarioEntity(UsuarioRequest usuarios) {
        return UsuarioEntity.builder()
                .nombreUsuario(usuarios.getNombreUsuario())
                .correo(usuarios.getCorreo())
                .password(this.passwordEncoder.encode(usuarios.getPassword()))
                .role(RolNombre.USER)
                .build();
    }

    private UsuarioResponse toUsuario(UsuarioEntity usuarioEntities) {
        return UsuarioResponse.builder()
                .id(usuarioEntities.getId())
                .nombreUsuario(usuarioEntities.getNombreUsuario())
                .correo(usuarioEntities.getCorreo())
                .password(usuarioEntities.getPassword())
                .role(usuarioEntities.getRole())
                .build();
    }


}
