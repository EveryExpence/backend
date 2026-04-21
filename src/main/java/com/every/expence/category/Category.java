package com.every.expence.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="category")
public class Category {
    @Id
    private String id;

    @Indexed(unique = false)
    private String userId;

    private String name;

    private String type;


    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public Category(String id, String userId, String name, String type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

    public Category(String userId, String name, String type) {
        this.userId = userId;
        this.name = name;
        this.type = type;
    }
    
    public Category() {
        
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


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
}
