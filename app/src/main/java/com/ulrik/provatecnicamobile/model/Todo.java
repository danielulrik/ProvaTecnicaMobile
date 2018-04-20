package com.ulrik.provatecnicamobile.model;

import com.raizlabs.android.dbflow.structure.BaseModel;

public class Todo extends BaseModel {

    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
}
