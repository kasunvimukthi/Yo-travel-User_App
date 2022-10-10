package com.yo.test.AdapterData;

import android.graphics.Bitmap;

public class DA1adapterData2 {
    private String name;
    private String c_ID;
    private Bitmap imageID;

    public DA1adapterData2(String name, Bitmap imageID, String c_ID) {
        this.name = name;
        this.imageID = imageID;
        this.c_ID = c_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImageID() {
        return imageID;
    }

    public void setImageID(Bitmap imageID) {
        this.imageID = imageID;
    }

    public String getC_ID() {
        return c_ID;
    }

    public void setC_ID(String c_ID) {
        this.c_ID = c_ID;
    }
}

