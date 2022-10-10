package com.yo.test.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.yo.test.Conn;
import com.yo.test.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PackageActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager3;
    ActivtiyAdapter1 activtiyAdapter1;
    TextView textView1;
    String name;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        Intent i = getIntent();
        name = i.getStringExtra("Name");
        activtiyAdapter1 = new ActivtiyAdapter1(this,name);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager3 = findViewById(R.id.activityPackageView);
        textView1 = findViewById(R.id.textView);

        mImageView = findViewById(R.id.imageView5);
        textView1.setText(name);
        viewPager3.setAdapter(activtiyAdapter1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager3.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        viewPager3.setVisibility(View.INVISIBLE);
        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();
        viewPager3.setVisibility(View.VISIBLE);

    }

    @SuppressLint("StaticFieldLeak")
    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";

        Blob img;
        byte b[];
        Bitmap bitmap;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st2 = con1.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM `t_activities` WHERE `A_Name` = '"+name+"'");

                while (rs2.next()) {
                    img = rs2.getBlob("A_Image");

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

            mImageView.setImageBitmap(bitmap);
        }
    }

}