package com.ulrik.provatecnicamobile.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ulrik.provatecnicamobile.cache.Cache;
import com.ulrik.provatecnicamobile.database.AppDatabase;

@Table(database = AppDatabase.class)
public class Post extends BaseRXModel {

    @PrimaryKey
    private int id;
    @Column
    private int userId;
    @Column
    private String title;
    @Column
    private String body;

    public Post() {
    }

    public String getUser() {
        return Cache.getUser(userId).getName();
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
