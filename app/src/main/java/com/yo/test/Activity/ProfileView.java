package com.yo.test.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.fragment.DrawerActivity3;
import com.yo.test.md5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileView extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";

    String User;
    String Email;
    String U_ID;

    ConstraintLayout constraintLayout;
    ProgressBar progressBar;

    TextView textView1, textView2, textView3, textView4, textView5, pass;
    Button Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        User = sharedPreferences.getString(KEY_NAME,null);
        Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

//        constraintLayout = findViewById(R.id.Details_Layout);
//        constraintLayout.setVisibility(View.GONE);
//
//        progressBar = findViewById(R.id.progressBar5);
//        progressBar.setVisibility(View.VISIBLE);



        Edit = findViewById(R.id.button7);
        textView1 = findViewById(R.id.editText1);
        textView2 = findViewById(R.id.editText);
        textView3 = findViewById(R.id.editText2);
        textView4 = findViewById(R.id.editText3);
        textView5 = findViewById(R.id.editText4);
        pass = findViewById(R.id.editText5);

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView1.getText().toString().equals("")){
                    Toast.makeText(ProfileView.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else
                if (textView2.getText().toString().equals("")){
                    Toast.makeText(ProfileView.this, "Please Enter Your E-mail Address", Toast.LENGTH_SHORT).show();
                }else
                if (textView3.getText().toString().equals("")){
                    Toast.makeText(ProfileView.this, "Please Enter Your Age", Toast.LENGTH_SHORT).show();
                }else
                if (textView4.getText().toString().equals("")){
                    Toast.makeText(ProfileView.this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
                }else
                if (textView5.getText().toString().equals("")){
                    Toast.makeText(ProfileView.this, "Please Enter Your Contact Number", Toast.LENGTH_SHORT).show();
                }else
                if (pass.getText().toString().equals("")){
                    Toast.makeText(ProfileView.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }else{
                    Getdata2 getdata2 = new Getdata2();
                    getdata2.execute();
                }
            }
        });

    }


    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String name = "";
        String number = "";
        String address = "";
        String age = "";
        String email = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);



                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM `users` WHERE User_ID = " + U_ID + "");

                    while (rs.next()) {

                        name =  rs.getString(3).toString();
                        number =  rs.getString(9).toString();
                        address =  rs.getString(7).toString();
                        age =  rs.getString(6).toString();
                        email =  rs.getString(8).toString();

                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            textView1.setText(name);
            textView2.setText(email);
            textView3.setText(age);
            textView4.setText(address);
            textView5.setText(number);

//        constraintLayout.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.GONE);
        }
    }

    private class Getdata2 extends AsyncTask<Void, Void, String> {

        String res = "";

        md5 encryption = new md5(pass.getText().toString());
        String pass1 = encryption.getEncryption();

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";
                String name = textView1.getText().toString();
                String age = textView3.getText().toString();
                String address = textView4.getText().toString();
                String email = textView2.getText().toString();
                String phone = textView5.getText().toString();

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE Email_Address = '" + Email + "' AND Password ='" + pass1 + "'");

                if (rs.next()) {
                    String query = "UPDATE users SET `Name` = '"+ name +"',`Age` = '"+ age +"',`Address` = '"+ address +"',`Email_Address` = '"+ email +"',`Phone_Number` = '"+ phone +"' WHERE User_ID = '" + U_ID + "'";
                    Statement st1 = con.createStatement();
                    int rs1 = st1.executeUpdate(query);

                    if (rs1 > 0) {
                        result = "Your Details Update Now";
                        Intent intent = new Intent(ProfileView.this, DrawerActivity3.class);
                        startActivity(intent);
                        finish();
                    }else{
                        result = "Something Went Wrong";
                    }

                }else{
                    result = "Your Password is Wrong";
                }
                res = result;
            }catch (Exception e){
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ProfileView.this, "" + result, Toast.LENGTH_SHORT).show();

        }
    }
}