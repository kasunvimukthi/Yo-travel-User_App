package com.yo.test.Invoice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yo.test.Conn;
import com.yo.test.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InvoiceCancel1 extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";

    Button yes, no;
    String I_ID, U_ID;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_cancel1);

        Intent i = getIntent();
        String i_id = i.getStringExtra("I_ID");

        I_ID = i_id;

        yes = findViewById(R.id.button9);
        no = findViewById(R.id.button17);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        String User = sharedPreferences.getString(KEY_NAME,null);
        String Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

//        Toast.makeText(this, ""+I_ID, Toast.LENGTH_SHORT).show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Run run = new Run();
                run.execute();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class Run extends AsyncTask<String, Void, String> {

        String res = "";
        String p_status = "";
        String status = "";
        String date = "";
        int T_Cost = 0;
        int A_Adult_Cost = 0;
        int A_Child_Cost = 0;
        int A_Cost = 0;
        int Total = 0;
        int Profit_Loss = 0;
        int New_Profit_Loss = 0;
        int Total2 = 0;
        String T_Date = "";
        int Child = 0;
        int Adult = 0;
        String t_code = "";
        int Passengers = 0;
        int Passengers2 = 0;
        int Passengers3 = 0;

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM `invoice` WHERE Invoice_Number = " + I_ID + "");

                while (rs.next()) {
                    T_Date = rs.getString("T_start_date");
                    Child = rs.getInt("U_children");
                    Adult = rs.getInt("U_adults");
                    t_code = rs.getString("T_ID");

                    p_status =  rs.getString("Status");
                    date =  rs.getString("I_Date");
                    T_Cost = rs.getInt("T_Cost");
                    A_Adult_Cost = rs.getInt("A_Adult_Cost");
                    A_Child_Cost = rs.getInt("A_Child_Cost");
                    A_Cost = A_Adult_Cost+A_Child_Cost;

                    Total = T_Cost - A_Cost;
                    Passengers = Child+Adult;

                    if (p_status.equals("Full Paid")){
                        status = "Payment Canceled";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery("SELECT * FROM `profit_loss` WHERE Date = '"+date+"'");

                        if (rs2.next()) {
                            Profit_Loss =  rs2.getInt("Profit_Loss");
                            New_Profit_Loss = Profit_Loss-Total;

                            String query4 = "UPDATE profit_loss SET Profit_Loss = '"+New_Profit_Loss+"' WHERE Date = '" + date + "'";
                            Statement st4 = con.createStatement();
                            st4.executeUpdate(query4);

                            Statement st5 = con.createStatement();
                            ResultSet rs5 = st5.executeQuery("SELECT * FROM prediction WHERE P_Start_Date = '"+T_Date+"' && P_ID ='"+t_code+"'");
                            while (rs5.next()) {
                                Passengers2 =  rs5.getInt("No_of_pacenger");
                                Passengers3 = Passengers2-Passengers;

                                String query6 = "UPDATE prediction SET No_of_pacenger='"+Passengers3+"' WHERE P_Start_Date='"+T_Date+"' && P_ID ='"+t_code+"'";
                                Statement st6 = con.createStatement();
                                st6.executeUpdate(query6);

                            }
                        }

                    }else{
                        status = "Canceled";
                    }

                    String query = "UPDATE invoice SET Status = '"+status+"' WHERE Invoice_Number = '" + I_ID + "'";
                    Statement st1 = con.createStatement();
                    int rs1 = st1.executeUpdate(query);

                    if (rs1 > 0) {
                        result = "Invoice Canceled";
                        finish();
                    }else{
                        result = "Invoice Not Cancel";
                    }
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
            Toast.makeText(InvoiceCancel1.this, "" + result, Toast.LENGTH_SHORT).show();
        }
    }

}