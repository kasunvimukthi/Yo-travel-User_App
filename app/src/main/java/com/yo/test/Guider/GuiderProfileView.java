package com.yo.test.Guider;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yo.test.Conn;
import com.yo.test.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GuiderProfileView extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";

    String User;
    String Email;
    String U_ID;

    TextView text1, text2, text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider_profile_view);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        User = sharedPreferences.getString(KEY_NAME,null);
        Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

        text1 = findViewById(R.id.editText5);
        text2 = findViewById(R.id.editText6);
        text3 = findViewById(R.id.editText7);

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();
    }

    private class Getdata1 extends AsyncTask<Void, Void, String> {

        Integer Loca;
        String res;
        String P_ID;
        String G_ID;
        String Name;
        String Cont;
        String Email1;
        String I_Date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                Statement st1 = con1.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `invoice` WHERE `User_ID` = "+ U_ID +" && `Status` = 'Full Paid' && 'T_end_date' >= '"+I_Date+"' ORDER BY `T_end_date` ASC LIMIT 1");

                if (rs1.next()) {
                    P_ID = rs1.getString(3);
                    Loca = rs1.getInt(18);


                        Statement st2 = con1.createStatement();
                        ResultSet rs2 = st2.executeQuery("SELECT * FROM `guider_alocate` WHERE `P_ID` = "+ P_ID +"");

                        if (rs2.next()) {
                            G_ID = rs2.getString(2);
                            Statement st3 = con1.createStatement();
                            ResultSet rs3 = st3.executeQuery("SELECT * FROM `guider` WHERE `ID` = "+ G_ID +"");

                            while (rs3.next()) {
                                Name = rs3.getString(2);
                                Cont = rs3.getString(3);
                                Email1 = rs3.getString(4);

                            }
                        }else{
                            result = "Guider isn't Allocate Yet";
                        }


                }else{
                    result = "Please Book Tour Package";
                }
                res = result;
            }catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("")) {
                Toast.makeText(GuiderProfileView.this, "" + result, Toast.LENGTH_SHORT).show();
            }else{
                text1.setText(Name);
                text2.setText(Cont);
                text3.setText(Email1);
            }
        }
    }

}