package com.every.expence.expenseRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndex(name = "idx_user_date", def = "{'userId': 1, 'date': 1}")
@Document(collection = "expenseRecord")
public class ExpenseRecord {
    @Id
    private String id;
    @Indexed
    private String userId;
    // TODO: Add after pr #15 will be merged
    // @Indexed(unique = true)
    // private PaymentMethod paymentMethod;
    // TODO: Add after category will be created and merged
    // @Indexed(unique = true)
    // private Category category;
    // TODO: Add after pr #13 will be merged
    // @Indexed(unique = true)
    // private Account accountId;
    private BigDecimal amount;
    @Indexed
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String description;
    private List<String> attachments;

    public ExpenseRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

}
