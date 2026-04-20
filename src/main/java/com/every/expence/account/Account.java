package com.every.expence.account;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class Account {
    @Id
    private String id;

    @Indexed
    private String ownerId;

    private String name;

    private String currency;

    private BigDecimal balance;

    @CreatedDate
    private Instant createdAt;

    public Account() {
    }

    public Account(String ownerId, String name, String currency, BigDecimal balance) {
        this.ownerId = ownerId;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
    }

    public Account(String id, String ownerId, String name, String currency, BigDecimal balance) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}