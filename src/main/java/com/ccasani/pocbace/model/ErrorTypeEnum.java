package com.ccasani.pocbace.model;

public enum ErrorTypeEnum {

    // @formatter:off
    CODE_AUTHENTICATION ("0-401","AUTHENTICATION-ERROR"),
    CODE_NOT_FOUND ("0-404","NOT_FOUND-ERROR"),
    CODE_BAD_REQUEST ("0-400","BAD_REQUEST-ERROR");
    // @formatter:on

    private String code;

    private String messageCode;


    ErrorTypeEnum(String code, String messageCode) {
        this.code = code;
        this.messageCode = messageCode;
    }

    public String getCode() {
        return code;
    }


    public String getMessageCode() {
        return messageCode;
    }


}
