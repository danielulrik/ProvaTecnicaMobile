package com.ulrik.provatecnicamobile.model;

public class Comment implements Resource{

    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public Comment() {
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
