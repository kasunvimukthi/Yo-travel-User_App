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

import com.yo.test.Booking.Booking;
import com.yo.test.Conn;
import com.yo.test.DrawerMain;
import com.yo.test.MainActivity;
import com.yo.test.R;
import com.yo.test.md5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeleteAccount extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";

    String User;
    String Email;
    String U_ID;

    TextView Pass;
    Button Confirm;
    String Pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        User = sharedPreferences.getString(KEY_NAME,null);
        Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

        Pass = findViewById(R.id.deletePass);
        Confirm = findViewById(R.id.deleteBtn);


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pass1 = Pass.getText().toString();

                if (Pass1.isEmpty()){
                    Toast.makeText(DeleteAccount.this, "Please Enter your Password"+Pass1, Toast.LENGTH_SHORT).show();
                }else{
                    Delete delete = new Delete();
                    delete.execute();
                }

            }
        });

    }

    private class Delete extends AsyncTask<String, Void, String> {

        String res = "";
        md5 encryption = new md5(Pass.getText().toString());
        String pass = encryption.getEncryption();
        String Pass2 = pass;

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
                String ID = sharedPreferences.getString(KEY_U_ID,null);

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE Email_Address = '" + Email + "' AND Password ='" + Pass2 + "'");

                if (rs.next()) {


                    String query = "DELETE FROM `users` WHERE `User_ID` = '"+ID+"'";
                    Statement st1 = con.createStatement();
                    int rs1 = st1.executeUpdate(query);

                    if (rs1 > 0) {
                        result = "Your Account Delete Now";
                        Intent intent = new Intent(DeleteAccount.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String name = null;
                        editor.putString(KEY_NAME,name);
                        editor.putString(KEY_EMAIL,name);
                        editor.putString(KEY_U_ID,name);
                        editor.commit();
                    }else{
                        result = "Something Went Wrong";
                    }
                }else
                {
                    result = "Your Entered Password is Wrong";
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
            Toast.makeText(DeleteAccount.this, "" + result, Toast.LENGTH_SHORT).show();

        }
    }
}