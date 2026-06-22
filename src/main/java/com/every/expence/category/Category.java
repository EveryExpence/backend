package com.every.expence.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@CompoundIndexes({
        @CompoundIndex(name = "uniq_user_name_type", def = "{'userId': 1, 'name': 1, 'type': 1}", unique = true)
})
public class Category {
    @Id
    private String id;

    @Indexed(unique = false)
    private String userId;

    private String name;

    private String type;

    private String icon;

    public Category(String name, String type, String icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public Category(String id, String userId, String name, String type, String icon) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public Category(String userId, String name, String type, String icon) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
