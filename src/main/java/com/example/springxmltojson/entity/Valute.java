package com.example.springxmltojson.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;

@JacksonXmlRootElement
public class Valute {
    @JacksonXmlProperty(isAttribute = true,localName = "Code")
    private String code;
    @JacksonXmlProperty(isAttribute = true,localName = "Nominal")
    private String nominal;
    @JacksonXmlProperty(isAttribute = true,localName = "Name")
    private String name;
    @JacksonXmlProperty(isAttribute = true,localName = "Value")
    private BigDecimal value;

    public String getCode() {
        return code;
    }

    public Valute setCode(String code) {
        this.code = code;
        return this;
    }

    public String getNominal() {
        return nominal;
    }

    public Valute setNominal(String nominal) {
        this.nominal = nominal;
        return this;
    }

    public String getName() {
        return name;
    }

    public Valute setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Valute setValue(BigDecimal value) {
        this.value = value;
        return this;
    }
}
