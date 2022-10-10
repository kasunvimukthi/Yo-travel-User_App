package com.yo.test.AdapterData;

import android.graphics.Bitmap;

import java.sql.Blob;

public class DA1adapterData1 {
    private String name;
    private String id;
    private Bitmap imageID;

    public DA1adapterData1(String name,String id, Bitmap imageID) {
        this.name = name;
        this.id = id;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImageID() {
        return imageID;
    }

    public void setImageID(Bitmap imageID) {
        this.imageID = imageID;
    }
}

