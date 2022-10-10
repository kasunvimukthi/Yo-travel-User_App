package com.yo.test.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yo.test.Conn;
import com.yo.test.DrawerMain;
import com.yo.test.Login;
import com.yo.test.R;
import com.yo.test.fragment.DrawerActivity3;
import com.yo.test.md5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PasswordChange extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";
    private static final String KEY_PASS = "Pass";


    String User;
    String Email;
    String U_ID;

    TextView textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        User = sharedPreferences.getString(KEY_NAME,null);
        Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

         textView1 = findViewById(R.id.editTextTextPassword);
         textView2 = findViewById(R.id.editTextTextPassword2);
         textView3 = findViewById(R.id.editTextTextPassword3);

        Button button1 = findViewById(R.id.button10);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textView11 = textView1.getText().toString();
                String textView22 = textView2.getText().toString();
                String textView33 = textView3.getText().toString();

                if(textView11.isEmpty()){
                    Toast.makeText(PasswordChange.this, "Please Enter Your Current Password", Toast.LENGTH_SHORT).show();
                }else
                 if (textView22.isEmpty()) {
                     Toast.makeText(PasswordChange.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                 }else
                 if (textView33.isEmpty()) {
                     Toast.makeText(PasswordChange.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                 }else
                 if (!textView22.equals(textView33)){
                     Toast.makeText(PasswordChange.this, "New Password & Confirm Password Not Equaled", Toast.LENGTH_SHORT).show();
                 }else
                 if (textView22.length() < 5) {
                     Toast.makeText(PasswordChange.this, "Password Should Be More Than 5 Characters", Toast.LENGTH_SHORT).show();
                 }else {
                     Password password = new Password();
                     password.execute();
                 }
            }
        });
    }

    private class Password extends AsyncTask<String, Void, String> {

        String res = "";


        md5 encryption = new md5(textView3.getText().toString());
        String pass = encryption.getEncryption();
        String U_Pass = pass;

        md5 encryption1 = new md5(textView1.getText().toString());
        String pass1 = encryption1.getEncryption();
        String U_Pass1 = pass1;

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
                String ID = sharedPreferences.getString(KEY_U_ID,null);
                String Email = sharedPreferences.getString(KEY_EMAIL,null);
                String Pass = sharedPreferences.getString(KEY_PASS,null);

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE Email_Address = '" + Email + "' AND Password ='" + U_Pass1 + "'");

                if (rs.next()) {
                    String query = "UPDATE users SET Password = '"+ U_Pass +"' WHERE User_ID = '" + ID + "'";
                    Statement st1 = con.createStatement();
                    int rs1 = st1.executeUpdate(query);

                    if (rs1 > 0) {
                        result = "Your Password Update Now";
                        Intent intent = new Intent(PasswordChange.this, DrawerActivity3.class);
                        startActivity(intent);
                        finish();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String name = null;
                        editor.putString(KEY_PASS,name);
                        editor.commit();

                        SharedPreferences.Editor editor2 = sharedPreferences.edit();
                        editor2.putString(KEY_PASS,U_Pass);
                        editor2.apply();

                    } else {
                        result = "Something Went to Wrong";
                    }
                } else {
                    result = "Your Password Not Match With Database";
                }


                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(PasswordChange.this, "" + result, Toast.LENGTH_SHORT).show();
//            Name.setText(result);

        }
    }
}