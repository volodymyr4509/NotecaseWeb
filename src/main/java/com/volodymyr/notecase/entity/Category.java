package com.volodymyr.notecase.entity;

import java.sql.Timestamp;

/**
 * Created by volodymyr on 03.01.16.
 */
public class Category {
    private int id;
    private String name;
    private int color;
    private int image;
    private Timestamp lastUpdateTimestamp;
    private boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Timestamp getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Timestamp lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", image=" + image +
                ", lastUpdateTimestamp=" + lastUpdateTimestamp +
                ", enabled=" + enabled +
                '}';
    }
}
