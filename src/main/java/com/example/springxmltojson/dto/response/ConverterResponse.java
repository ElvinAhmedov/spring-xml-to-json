package com.example.springxmltojson.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConverterResponse implements Serializable {
   private String currency;
   private BigDecimal amount;

    public String getCurrency() {
        return currency;
    }

    public ConverterResponse setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ConverterResponse setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
