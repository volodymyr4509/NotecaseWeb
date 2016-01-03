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
}
