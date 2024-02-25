package com.ccasani.pocbace.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageComponent {
    private final MessageSource messageSource;

    public String getMessagesKey(String key){
        return this.messageSource.getMessage(key,null, null, LocaleContextHolder.getLocale());
    }
}
