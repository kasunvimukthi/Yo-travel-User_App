package com.yo.test.AdapterData;

import android.graphics.Bitmap;

public class InvoiceAdapterData {
    private String name;
    private String id;
    private String status;
    private String i_date;
    private String i_S_date;

    public InvoiceAdapterData(String name, String id, String status, String i_date, String i_S_date) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.i_date = i_date;
        this.i_S_date = i_S_date;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getI_date() {
        return i_date;
    }

    public void setI_date(String i_date) {
        this.i_date = i_date;
    }

    public String getI_S_date() {
        return i_S_date;
    }

    public void setI_S_date(String i_S_date) {
        this.i_S_date = i_S_date;
    }
}

