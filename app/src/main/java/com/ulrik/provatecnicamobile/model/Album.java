package com.ulrik.provatecnicamobile.model;

import com.raizlabs.android.dbflow.structure.BaseModel;

public class Album implements Resource {

    private int userId;
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return "";
    }
}
