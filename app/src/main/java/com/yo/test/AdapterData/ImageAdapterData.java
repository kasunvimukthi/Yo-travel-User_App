package com.yo.test.AdapterData;

import android.graphics.Bitmap;

public class ImageAdapterData {
    private Bitmap img;

    public ImageAdapterData(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}

