package com.ccasani.pocbace.model;

import java.util.Arrays;

public enum ReporteEnum {

    // @formatter:off
    XLSX("00t"),
    PDF("009"),
    PPT("HBT66"),
    NONE("");
    // @formatter:on
    private String code;

    ReporteEnum(String code) {
        this.code = code;
    }

    public static ReporteEnum reporteEnumByCode(String  code){
        return Arrays.stream(values()).filter(t->t.getCode().equals(code)).findAny().orElse(NONE);
    }

    public String getCode() {
        return code;
    }
}
