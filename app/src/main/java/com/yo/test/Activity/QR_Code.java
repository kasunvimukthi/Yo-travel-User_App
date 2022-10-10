package com.yo.test.Activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.yo.test.R;

public class QR_Code extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";
    private static final String KEY_PASS = "Pass";


    String User;
    String Email;
    String U_ID;

    ImageView imageView;

    ConstraintLayout constraintLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        imageView = findViewById(R.id.imageView2);

        constraintLayout = findViewById(R.id.QR_Layout);
        constraintLayout.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);

        Run run = new Run();
        run.execute();


    }

    private class Run extends AsyncTask<Void, Void, Void> {

        Bitmap bitmap;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
                String ID = sharedPreferences.getString(KEY_U_ID,null);
                String Email = sharedPreferences.getString(KEY_EMAIL,null);
                String Pass = sharedPreferences.getString(KEY_PASS,null);

                String QR = Email+Pass;
                MultiFormatWriter writer = new MultiFormatWriter();
                BitMatrix bitMatrix = writer.encode(QR, BarcodeFormat.QR_CODE,500,500);

                BarcodeEncoder encoder = new BarcodeEncoder();
                bitmap = encoder.createBitmap(bitMatrix);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            imageView.setImageBitmap(bitmap);

            progressBar.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
        }
    }
}