package com.volodymyr.notecase.entity;

import java.sql.Timestamp;

/**
 * Created by volodymyr on 03.01.16.
 */
public class User {
    private int id;
    private String userName;
    private String userPassword;
    private String email;
    private Timestamp lastUpdateTimestamp;
    private boolean enabled;
}
