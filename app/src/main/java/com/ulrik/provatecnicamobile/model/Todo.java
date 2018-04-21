package com.ulrik.provatecnicamobile.model;

public class Todo implements Resource{

    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public String getUser() {
        return "";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return completed ? "Finalizada" : "Em andamento";
    }

}
