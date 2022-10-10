package com.yo.test.AdapterData;

import android.graphics.Bitmap;

public class DA1adapterData3 {
    private String packagename, id, location, status, Sdate, Edate;
    private Bitmap image;

    public DA1adapterData3(String packagename, String id, String location, String status, String sdate, String edate,  Bitmap image) {
        this.packagename = packagename;
        this.id = id;
        this.location = location;
        this.status = status;
        Sdate = sdate;
        Edate = edate;
        this.image = image;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSdate() {
        return Sdate;
    }

    public void setSdate(String sdate) {
        Sdate = sdate;
    }

    public String getEdate() {
        return Edate;
    }

    public void setEdate(String edate) {
        Edate = edate;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

