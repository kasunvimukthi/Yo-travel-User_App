package com.yo.test.Booking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.yo.test.Conn;
import com.yo.test.DrawerMain;
import com.yo.test.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Booking extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";

    String User;
    String Email;
    String U_ID;
    String P_ID, A_Adult_Cost1, A_Child_Cost1;

    ConstraintLayout constraintLayout;
    ProgressBar progressBar;

    TextView adult, child, P_Adults, P_A_Total, P_Child, P_C_Total, Sub_Total, Addit, P_E_D, P_S_D,U_Name,U_Email,U_Address,P_Code,P_Name;

    Button book;

    String email = "yotravelmail@gmail.com";
    String password = "mrdylmwjwqagvlra";
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        User = sharedPreferences.getString(KEY_NAME,null);
        Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

        Intent i = getIntent();
        String p_id = i.getStringExtra("P_ID");

        P_ID = p_id;

         adult = findViewById(R.id.N_Adults);
         child = findViewById(R.id.N_Child);
         P_Adults = findViewById(R.id.P_Adults);
         P_A_Total = findViewById(R.id.P_A_Total);
         P_Child = findViewById(R.id.P_Child);
         P_C_Total = findViewById(R.id.P_C_Total);
         Sub_Total = findViewById(R.id.Sub_Total);
         Addit = findViewById(R.id.Additional);
         P_E_D = findViewById(R.id.P_E_D);
         P_S_D = findViewById(R.id.P_S_D);
         U_Name = findViewById(R.id.U_Name);
         U_Email = findViewById(R.id.U_Email);
         U_Address = findViewById(R.id.U_Address);
         P_Code = findViewById(R.id.P_Code);
         P_Name = findViewById(R.id.P_Name);

        book = findViewById(R.id.button11);

        Properties mProperties = new Properties();
        mProperties.put("mail.smtp.auth","true");
        mProperties.put("mail.smtp.starttls.enable","true");
        mProperties.put("mail.smtp.host","smtp.gmail.com");
        mProperties.put("mail.smtp.port","587");

         session = Session.getInstance(mProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,password);
            }
        });

         book.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Integer X = Integer.valueOf(adult.getText().toString());
                 Integer Y = Integer.valueOf(child.getText().toString());
                 Integer XY = X+Y;

                 if (XY <= 0) {
                     Toast.makeText(Booking.this, "Please Enter Passenger More Than Zero", Toast.LENGTH_SHORT).show();
                 }else{
                     Run3 run3 = new Run3();
                     run3.execute();
                 }
             }
         });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (adult.getText().toString().equals("")){
                    adult.setText("0");
                }else
                if (child.getText().toString().equals("")){
                    child.setText("0");
                }else {
                    Double a = Double.valueOf(adult.getText().toString());
                    Double b = Double.valueOf(P_Adults.getText().toString());

                    Double total1 = a * b;
                    P_A_Total.setText("" + total1);

                    Double c = Double.valueOf(child.getText().toString());
                    Double d = Double.valueOf(P_Child.getText().toString());

                    Double total2 = c * d;
                    P_C_Total.setText("" + total2);

                    Double x = total1 + total2;

                    Sub_Total.setText("" + x);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        adult.addTextChangedListener(textWatcher);
        child.addTextChangedListener(textWatcher);

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String name = "";
        String address = "";
        String email = "";
        String p_name = "";
        String s_date = "";
        String e_date = "";
        String p_adult = "";
        String p_child = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM `users` WHERE User_ID = " + U_ID + "");

                while (rs.next()) {

                    name =  rs.getString(2).toString();
//                    number =  rs.getString(9).toString();
                    address =  rs.getString(7).toString();
//                    age =  rs.getString(6).toString();
                    email =  rs.getString(8).toString();

                    Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery("SELECT * FROM `package` WHERE Travel_ID = " + P_ID + "");

                    while (rs1.next()) {

                        p_name =  rs1.getString(3).toString();
                        s_date =  rs1.getString(12).toString();
                        e_date =  rs1.getString(13).toString();
                        p_adult =  rs1.getString(8).toString();
                        p_child =  rs1.getString(9).toString();
                        A_Adult_Cost1 =  rs1.getString(6).toString();
                        A_Child_Cost1 =  rs1.getString(7).toString();
                    }
                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            U_Name.setText(name);
            U_Email.setText(email);
            U_Address.setText(address);
            P_Code.setText(P_ID);
            P_Name.setText(p_name);
            P_S_D.setText(s_date);
            P_E_D.setText(e_date);
            P_Adults.setText(p_adult);
            P_Child.setText(p_child);
        }
    }

    private class Run3 extends AsyncTask<Void, Void, String> {

        String res = "";
        String I_Date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String I_Time = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
        String T_end_date = P_E_D.getText().toString();
        String T_start_date = P_S_D.getText().toString();
        int U_children = Integer.parseInt(child.getText().toString());
        int U_adults = Integer.parseInt(adult.getText().toString());
        String U_child_cost = P_Child.getText().toString();
        String U_adult_cost = P_Adults.getText().toString();
        String A_Adult_Cost = A_Adult_Cost1;
        String A_Child_Cost = A_Child_Cost1;
        String P_type = "Bank Deposit";
        String T_Cost = Sub_Total.getText().toString();
        String Request = Addit.getText().toString();
        String Statu = "Not Paid";
        String email1 = U_Email.getText().toString();
        String name = U_Name.getText().toString();


        @Override
        protected String doInBackground(Void... voids) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                String quary2 = "SELECT * FROM package WHERE Travel_ID = '" + P_ID + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(quary2);

                while (rs2.next()) {

                    int t_adult_actual_cost = rs2.getInt("T_Adult_Cost");
                    int t_child_actual_cost = rs2.getInt("T_Child_Cost");

                    int TAAC = U_adults*t_adult_actual_cost;
                    int TCAC = U_children*t_child_actual_cost;

                    String quary = "INSERT INTO `invoice`(`User_ID`, `T_ID`, `I_Date`, `I_Time`, `T_end_date`, `T_start_date`, `U_children`, `U_adults`, `U_child_cost`, `U_adult_cost`, `A_Adult_Cost`, `A_Child_Cost`, `P_type`, `T_Cost`, `Request`, `Status`) " +
                            "VALUES ('" + U_ID + "','" + P_ID + "','" + I_Date + "','" + I_Time + "','" + T_end_date + "','" + T_start_date + "','" + U_children + "','" + U_adults + "','" + U_child_cost + "','" + U_adult_cost + "','" + TAAC + "','" + TCAC + "','" + P_type + "','" + T_Cost + "','" + Request + "','" + Statu + "')";
                    Statement st1 = con.createStatement();
                    int rs1 = st1.executeUpdate(quary);

                    if (rs1 > 0) {

                        Statement st3 = con.createStatement();
                        ResultSet rs3 = st3.executeQuery("SELECT * FROM `profit_loss` WHERE Date = '"+I_Date +"'");

                        if (rs3.next()) {

                        }else{
                                String quary4 = "INSERT INTO profit_loss (Date, Profit_Loss) VALUES ('"+I_Date+"','0')";
                                Statement st4 = con.createStatement();
                                int rs4 = st4.executeUpdate(quary4);

                        }
                        result = "Package add into your book list. Please upload payment slip to be confirm";

                    } else {
                        result = "Something Went Wrong!";
                    }
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
            Toast.makeText(Booking.this, "" + result, Toast.LENGTH_SHORT).show();
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email1));
                message.setSubject("Regarding yo-travel package booking");
                message.setText(String.valueOf(Html.fromHtml("<h3>Dear "+name+",</h3>" +
                        "<p></p>" +
                        "<p>"+result+"</p>" +
                        "<p>Yo-travel(PVT)LTD</p>" +
                        "<p>123/01,</p>" +
                        "<p>Colombo,</p>" +
                        "<p>Sri Lanka.</p>" +
                        "<p>Tel : 0123456789</p>")));

                new SendMail().execute(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
    }

    private class SendMail extends AsyncTask<Message,String,String> {
        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Booking.this,"Please Wait","Sending Mail",true,false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressDialog.dismiss();
            if (s.equals("Success")){
                AlertDialog.Builder builder = new AlertDialog.Builder(Booking.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail send Successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Booking.this, DrawerMain.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }else{
                Toast.makeText(Booking.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}