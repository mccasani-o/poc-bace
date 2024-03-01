package com.ccasani.pocbace.service;

import com.ccasani.pocbace.model.UsuarioEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class UsuarioEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    void handleAddEmployeeEvent(UsuarioEvent event) {
        log.info("Event: {}", event.toString());
        log.info("Eviando correo a ... {}", event.getUsuarioEntity().getCorreo());
    }

}
