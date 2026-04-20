package com.every.expence.expenseRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "expenseRecord")
public class ExpenseRecord {
    @Id
    private String id;
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
    private Date date;
    private Time time;
    private String location;
    private String description;
    private String[] attachments;

    public ExpenseRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

}
