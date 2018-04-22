package com.ulrik.provatecnicamobile.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ulrik.provatecnicamobile.database.AppDatabase;

@Table(database = AppDatabase.class)
public class Todo extends BaseRXModel {

    @PrimaryKey
    private int id;
    @Column
    private int userId;
    @Column
    private String title;
    @Column
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getUser() {
        return "";
    }

    public String getStatus() {
        return completed ? "Finalizada" : "Em andamento";
    }

}
