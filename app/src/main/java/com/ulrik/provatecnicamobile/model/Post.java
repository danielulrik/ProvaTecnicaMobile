package com.ulrik.provatecnicamobile.model;

public class Post implements Resource{

    private int userId;
    private int id;
    private String title;
    private String body;

    public Post() {
    }

    public String getUser() {
        return "";
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
