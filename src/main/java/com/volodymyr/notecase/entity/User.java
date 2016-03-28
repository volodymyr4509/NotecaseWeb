package com.volodymyr.notecase.entity;

import java.sql.Timestamp;

/**
 * Created by volodymyr on 03.01.16.
 */
public class User {

    private int id;
    private String name;
    private String idToken;
    private String authToken;
    private String email;
    private Timestamp lastUpdateTimestamp;
    private boolean enabled = true;

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

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idToken='" + idToken + '\'' +
                ", authToken='" + authToken + '\'' +
                ", email='" + email + '\'' +
                ", lastUpdateTimestamp=" + lastUpdateTimestamp +
                ", enabled=" + enabled +
                '}';
    }
}
