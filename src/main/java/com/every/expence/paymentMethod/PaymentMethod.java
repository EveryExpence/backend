package com.every.expence.paymentMethod;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paymentMethods")
@CompoundIndex(name = "uniq_user_payment_method_name", def = "{'userId':1, 'name':1}", unique = true)
public class PaymentMethod {
    @Id
    private String id;

    private String userId;

    private String name;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentMethod() {
    }

    public PaymentMethod(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

}
