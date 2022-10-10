package com.yo.test.Accommodation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.yo.test.Conn;
import com.yo.test.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Accommodation extends AppCompatActivity {

    String Name;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;
    ImageView imageView;
    WebView mWebView;

    ConstraintLayout constraintLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);

        Intent i = getIntent();
        Name = i.getStringExtra("name");

        textView1 = findViewById(R.id.textView84);
        textView2 = findViewById(R.id.textView86);
        textView3 = findViewById(R.id.textView95);
        textView4 = findViewById(R.id.textView91);
        textView5 = findViewById(R.id.textView92);
        textView6 = findViewById(R.id.textView93);
        textView7 = findViewById(R.id.textView94);

        imageView = findViewById(R.id.imageView7);

        mWebView = findViewById(R.id.webView3);

        constraintLayout = findViewById(R.id.mainLayout);
        progressBar = findViewById(R.id.progressBar9);

        constraintLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        textView1.setText(Name);

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String A_Location = "";
        String Name1 = "";
        String A_summary = "";
        String A_Details = "";
        String Style = "";
        String No_of_rooms = "";
        String Key_features = "";
        String Link = "";

        Blob img;
        byte b[];
        Bitmap bitmap;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);



                Statement st2 = con1.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM `t_accommodation` WHERE `A_Name` = '"+Name+"'");

                while (rs2.next()) {
                    A_Location = rs2.getString("A_Location").toString();
                    A_summary = rs2.getString("A_summary").toString();
                    A_Details = rs2.getString("A_Details").toString();
                    img = rs2.getBlob("A_Image");
                    Style = rs2.getString("Style").toString();
                    No_of_rooms = rs2.getString("No_of_rooms").toString();
                    Key_features = rs2.getString("Key_features").toString();
                    Link = rs2.getString("A_Link").toString();

                    b = img.getBytes(1, (int) img.length());
                    bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
//            textView1.setText(Name1);
            textView2.setText(A_summary);
            textView3.setText(A_Details);
            textView4.setText(A_Location);
            textView5.setText(Style);
            textView6.setText(No_of_rooms);
            textView7.setText(Key_features);
            mWebView.loadUrl(Link);

            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(true);

            imageView.setImageBitmap(bitmap);
            constraintLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}