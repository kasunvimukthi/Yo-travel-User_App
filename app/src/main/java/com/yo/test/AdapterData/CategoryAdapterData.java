package com.yo.test.AdapterData;

import android.graphics.Bitmap;

public class CategoryAdapterData {
    private String P_id;
    private  String Date1;
    private  String Date2;
    private  String PackageName;
    private  String PackageLocation;
    private  String Summery;

    private Bitmap imgID;

    public CategoryAdapterData(String p_id, String date1, String date2, String packageName, String packageLocation, String summery, Bitmap imgID) {
        P_id = p_id;
        Date1 = date1;
        Date2 = date2;
        PackageName = packageName;
        PackageLocation = packageLocation;
        Summery = summery;
        this.imgID = imgID;
    }


    public String getP_id() {
        return P_id;
    }

    public void setP_id(String p_id) {
        P_id = p_id;
    }

    public String getDate1() {
        return Date1;
    }

    public void setDate1(String date1) {
        Date1 = date1;
    }

    public String getDate2() {
        return Date2;
    }

    public void setDate2(String date2) {
        Date2 = date2;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getPackageLocation() {
        return PackageLocation;
    }

    public void setPackageLocation(String packageLocation) {
        PackageLocation = packageLocation;
    }

    public String getSummery() {
        return Summery;
    }

    public void setSummery(String summery) {
        Summery = summery;
    }

    public Bitmap getImgID() {
        return imgID;
    }

    public void setImgID(Bitmap imgID) {
        this.imgID = imgID;
    }
}
