package com.ulrik.provatecnicamobile.model;

import com.raizlabs.android.dbflow.structure.BaseModel;

public class Album extends BaseModel {

    private int userId;
    private int id;
    private String title;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
