package com.yo.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Sign_up extends AppCompatActivity {

    Button Login_link, Sign_up;
    TextView UserName, FullName, Email, Address, Password, Re_Password, Age, C_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Login_link = (Button) findViewById(R.id.Login_link);
        Sign_up = (Button) findViewById(R.id.sign_btn);
        UserName = findViewById(R.id.userName);
        FullName = findViewById(R.id.userFullName);
        Email = findViewById(R.id.userEmail);
        Age = findViewById(R.id.userAge);
        Address = findViewById(R.id.userAddress);
        C_Number = findViewById(R.id.userContact);
        Password = findViewById(R.id.userPassword);
        Re_Password = findViewById(R.id.userRePassword);


        Login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this, Login.class);
                startActivity(intent);
            }
        });

        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String U_Name = UserName.getText().toString();
                String U_Full = FullName.getText().toString();
                String U_Email = Email.getText().toString();
                String U_Age = Age.getText().toString();
                String U_Address = Address.getText().toString();
                String U_Cont = C_Number.getText().toString();
                String U_Pass = Password.getText().toString();
                String U_ReP = Re_Password.getText().toString();

                if (U_Name.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                } else
                    if (U_Full.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Enter Full Name", Toast.LENGTH_SHORT).show();
                } else
                    if (U_Email.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else
                    if (U_Age.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Enter Age", Toast.LENGTH_SHORT).show();
                } else
                    if (U_Address.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else
                    if (U_Cont.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else
                    if (U_Pass.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else
                    if (U_ReP.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Please Re-Enter Password", Toast.LENGTH_SHORT).show();
                } else
                    if (!U_Pass.equals(U_ReP)) {
                    Toast.makeText(Sign_up.this, "Password Not Match With Re-Enter Password", Toast.LENGTH_SHORT).show();
                } else
                    if (U_Pass.length() < 5) {
                    Toast.makeText(Sign_up.this, "Password Must be Greater Than 5 Charactors", Toast.LENGTH_SHORT).show();
                } else
                    if (!Patterns.EMAIL_ADDRESS.matcher(U_Email).matches()) {
                    Toast.makeText(Sign_up.this, "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();
                } else
                    if (!Patterns.PHONE.matcher(U_Cont).matches()) {
                    Toast.makeText(Sign_up.this, "Please Enter a Valid Contact Number", Toast.LENGTH_SHORT).show();
                } else {
                    CheckLogin checkLogin = new CheckLogin();
                    checkLogin.execute("");
                }
            }
        });
    }

    private class CheckLogin extends AsyncTask<String, Void, String> {

        String res = "";
        String U_Status = "1";
        String U_Name = UserName.getText().toString();
        String U_Full = FullName.getText().toString();
        String U_Email = Email.getText().toString();
        String U_Age = Age.getText().toString();
        String U_Address = Address.getText().toString();
        String U_Cont = C_Number.getText().toString();

        md5 encryption = new md5(Re_Password.getText().toString());
        String pass = encryption.getEncryption();
        String U_Pass = pass;

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE Email_Address = '" + U_Email + "'");

                if (rs.next()) {
                    result = "This Email Address Already Have Registerd With User!";
                }else {
                    String quary = "INSERT INTO `users`(`User_Name`, `Name`, `Password`, `Age`, `Address`, `Email_Address`, `Phone_Number`, `U_Status`) VALUES ('" + U_Name + "','" + U_Full + "','" + U_Pass + "','" + U_Age + "','" + U_Address + "','" + U_Email + "','" + U_Cont + "','" + U_Status + "')";
                    Statement st1 = con.createStatement();
                    int rs1 = st1.executeUpdate(quary);

                    if (rs1 > 0) {
                        result = "Your Account Was Created Now";
                        Intent intent = new Intent(Sign_up.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        result = "Something Went Wrong!";
                    }
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
            Toast.makeText(Sign_up.this, "" + result, Toast.LENGTH_SHORT).show();
//            Name.setText(result);

        }
        }

}