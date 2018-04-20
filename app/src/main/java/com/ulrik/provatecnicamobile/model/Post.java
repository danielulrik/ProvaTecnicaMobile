package com.ulrik.provatecnicamobile.model;

import com.raizlabs.android.dbflow.structure.BaseModel;

public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

    public Post() {
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
