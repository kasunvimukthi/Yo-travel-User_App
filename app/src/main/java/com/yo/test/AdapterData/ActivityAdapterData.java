package com.yo.test.AdapterData;

import android.graphics.Bitmap;

public class ActivityAdapterData {
    private String name, location, summary;
    private Bitmap img;

    public ActivityAdapterData(String name, String location, String summary, Bitmap img) {
        this.name = name;
        this.location = location;
        this.summary = summary;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}

