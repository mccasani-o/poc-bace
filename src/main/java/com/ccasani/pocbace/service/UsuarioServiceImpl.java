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
