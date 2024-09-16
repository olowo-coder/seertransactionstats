package com.org.seerbitbanking.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Transaction {
    private BigDecimal amount;
    private ZonedDateTime timestamp;

    public Transaction(BigDecimal amount, ZonedDateTime timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
