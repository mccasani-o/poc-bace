package com.ccasani.pocbace;

import com.ccasani.pocbace.model.entity.UsuarioEntity;
import com.ccasani.pocbace.repository.UsuarioRepository;
import com.ccasani.pocbace.security.model.RolNombre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class PocBaceApplication implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PocBaceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.lisUsers().forEach(usuarioEntity -> {
            this.usuarioRepository.save(usuarioEntity);
            log.info("{}", usuarioEntity.getId());
        });

        this.demo();

    }

    List<UsuarioEntity> lisUsers() {
        return Arrays.asList(UsuarioEntity.builder()
                .nombreUsuario("mauricio")
                .correo("mau@gmail.com")
                .password(this.passwordEncoder.encode("1234"))
                .role(RolNombre.USER).build());
    }

    void demo() {
        LocalDate now = LocalDateTime.now().atZone(ZoneId.of("America/Lima")).toLocalDate();
        LocalDate dateOut = now.equals(now.with(TemporalAdjusters.lastDayOfMonth())) ? now :
                now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate dateIn = dateOut.with(TemporalAdjusters.lastDayOfMonth());
        log.info(dateIn.toString());
    }
}
