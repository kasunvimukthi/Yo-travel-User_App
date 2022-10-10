package com.yo.test.Details;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.yo.test.Booking.Booking;
import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.adaptor.UpComingTabAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class upComingItemDetails extends AppCompatActivity {

    TabLayout tabLayout;
    UpComingTabAdapter upComingTabAdapter;
    ViewPager2 viewPager2;
    TextView textView;
    Button button;

    ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_U_ID = "";

    String U_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_item_details);

        Intent i = getIntent();
        String p_id = i.getStringExtra("P_ID");
        upComingTabAdapter = new UpComingTabAdapter(this,p_id);

        mProgressDialog = ProgressDialog.show(this,"Please Wait","Data Loading...",true,false);
        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

        tabLayout = findViewById(R.id.upComingTabLayout);
        viewPager2 = findViewById(R.id.upComingViewPage1);
         textView = findViewById(R.id.textView24);
        button = findViewById(R.id.button);
        viewPager2.setAdapter(upComingTabAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        viewPager2.setVisibility(View.GONE);
        Getdata1 getdata1 = new Getdata1(p_id);
        getdata1.execute();
        viewPager2.setVisibility(View.VISIBLE);
        mProgressDialog.dismiss();
    }

    @SuppressLint("StaticFieldLeak")
    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        private final String P_id;

        private String text,s_date, e_date;


        public Getdata1(String p_id) {
            this.P_id = p_id;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st1 = con1.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `package` WHERE `Travel_ID` = "+ P_id +"");

                while (rs1.next()) {
                    text = rs1.getString(3);
                    s_date = rs1.getString(12);
                    e_date = rs1.getString(13);



                    Statement st2 = con1.createStatement();
                    ResultSet rs2 = st2.executeQuery("SELECT * FROM `invoice` WHERE `User_ID` = "+ U_ID +" && `T_start_date` BETWEEN '"+ s_date +"' AND '"+ e_date +"' && `T_end_date` BETWEEN '"+ s_date +"' AND '"+ e_date +"' && `Status` = 'Full Paid'");

                    if (rs2.next()) {

                        button.setId(Integer.parseInt(P_id));

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(upComingItemDetails.this, "You Can't Book This Package", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        button.setId(Integer.parseInt(P_id));

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(upComingItemDetails.this, Booking.class);
                                intent.putExtra("P_ID",P_id);
                                startActivity(intent);
                            }
                        });
                    }



                }

            } catch (Exception e) {
                res = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            textView.setText(text);
        }
    }
}