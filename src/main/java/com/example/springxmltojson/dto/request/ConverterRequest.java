package com.example.springxmltojson.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConverterRequest implements Serializable {

    private String currency;
    private BigDecimal amount;
    private String convertedTo;

    public String getCurrency() {
        return currency;
    }

    public ConverterRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ConverterRequest setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getConvertedTo() {
        return convertedTo;
    }

    public ConverterRequest setConvertedTo(String convertedTo) {
        this.convertedTo = convertedTo;
        return this;
    }
}
