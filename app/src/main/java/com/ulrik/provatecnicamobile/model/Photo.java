package com.ulrik.provatecnicamobile.model;

public class Photo {

    private int id;
    private int albumId;
    private String url;
    private String thumbnailUrl;

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
