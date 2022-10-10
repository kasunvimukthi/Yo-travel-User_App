package com.yo.test.Invoice;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.yo.test.AdapterData.TCAdapterData;
import com.yo.test.Conn;
import com.yo.test.Details.upComingItemDetails;
import com.yo.test.DrawerMain;
import com.yo.test.Login;
import com.yo.test.MainActivity;
import com.yo.test.R;
import com.yo.test.Sign_up;
import com.yo.test.adaptor.TCAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InvoiceView extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";

    String User;
    String Email;
    String U_ID;
    String I_ID;
    String PType;
    String T_C;


    TextView reciver, email, invoiceNo, iDate, pName, pType, status1, sDate, eDate, d1, d2, q1, q2, c1, c2, tc1, tc2, st, request1;
    Button PDF;

    RecyclerView recyclerView;
    TCAdapter tcAdapter;
    ArrayList<TCAdapterData> list1;
    WebView webView;

    ConstraintLayout pdfView;
    ScrollView scrollView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);

        Intent i = getIntent();
        String i_id = i.getStringExtra("I_ID");

        I_ID = i_id;

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        User = sharedPreferences.getString(KEY_NAME,null);
        Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

        recyclerView = findViewById(R.id.recyclerView4);

//        scrollView2 =findViewById(R.id.scrollView2);
//        scrollView2.setVisibility(View.VISIBLE);
//
//        pdfView = findViewById(R.id.pdfView);
//        pdfView.setVisibility(View.GONE);

        reciver = findViewById(R.id.textView40);
        email = findViewById(R.id.textView42);
        invoiceNo = findViewById(R.id.textView44);
        iDate = findViewById(R.id.textView47);
        pName = findViewById(R.id.textView48);
        pType = findViewById(R.id.textView51);
        status1 = findViewById(R.id.textView52);
        sDate = findViewById(R.id.textView57);
        eDate = findViewById(R.id.textView58);

        d1 = findViewById(R.id.textView63);
        d2 = findViewById(R.id.textView67);
        q1 = findViewById(R.id.textView64);
        q2 = findViewById(R.id.textView68);
        c1 = findViewById(R.id.textView65);
        c2 = findViewById(R.id.textView69);
        tc1 = findViewById(R.id.textView66);
        tc2 = findViewById(R.id.textView70);
        st = findViewById(R.id.textView72);

        webView = findViewById(R.id.webView);
        request1 = findViewById(R.id.textView74);
        PDF = findViewById(R.id.button14);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();

        reciver.setText(User);

        email.setText(Email);

        invoiceNo.setText("# "+I_ID);

        list1 = new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////
//            }
//        }).start();

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        PType = pType.getText().toString();

    }


    private void createPDF() {
        PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(InvoiceView.this, InvoiceViewPDF.class);
//                startActivity(intent);



//                scrollView2 =findViewById(R.id.scrollView2);
//                scrollView2.setVisibility(View.GONE);
////
//                pdfView = findViewById(R.id.pdfView);
//                pdfView.setVisibility(View.VISIBLE);

//                PdfDocument pdfDocument = new PdfDocument();
//                Paint paint = new Paint();
//
//                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
//                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
//
//                Canvas canvas = page.getCanvas();
//
//                canvas.drawText("test",40,50,paint);
//
//
//
//                pdfDocument.finishPage(page);
//
//                File file = new File(Environment.getExternalStorageDirectory(),"/Invoice.pdf");
//
//                try {
//                    pdfDocument.writeTo(new FileOutputStream(file));
//                    Toast.makeText(InvoiceView.this, "PDF Created", LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(InvoiceView.this, "Something Went Wrong, PDF Not Create", LENGTH_SHORT).show();
//                }
//
//                pdfDocument.close();
            }
        });
    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String u_id = "";
                String t_id = "";
                String I_Date = "";
                String T_end_date = "";
                String T_start_date = "";
                int U_children;
                int U_adults;
                int U_child_cost;
                int U_adult_cost;
                String P_type = "";
                int T_Cost;
                String Request = "";
                String Status = "";
                String p_Name = "";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM `invoice` WHERE Invoice_Number  = "+I_ID+"");

                while (rs.next()) {

                    u_id =  rs.getString(2).toString();
                    t_id =  rs.getString(3).toString();
                    I_Date =  rs.getString(4).toString();
                    T_end_date =  rs.getString(6).toString();
                    T_start_date =  rs.getString(7).toString();
                    U_children =  rs.getInt(8);
                    U_adults =  rs.getInt(9);
                    U_child_cost =  rs.getInt(10);
                    U_adult_cost =  rs.getInt(11);
                    P_type =  rs.getString(14).toString();
                    T_Cost =  rs.getInt(15);
                    Request =  rs.getString(16).toString();
                    Status =  rs.getString(17).toString();

                    Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery("SELECT * FROM `package` WHERE Travel_ID  = "+t_id+"");

                    while (rs1.next()) {
                        p_Name =  rs1.getString(3).toString();

                    }

                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery("SELECT * FROM `t_conditions` WHERE T_ID  = " +t_id+"");

                    while (rs2.next()) {
                        T_C =  rs2.getString(3).toString();

                        list1.add(new TCAdapterData(T_C));
                    }
                    new run1(p_Name,I_Date,T_end_date,T_start_date,U_children,U_adults,U_child_cost,U_adult_cost,P_type,T_Cost,Request,Status);

                }

            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager layoutManager1 = new  LinearLayoutManager(InvoiceView.this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager1);
            tcAdapter = new TCAdapter(InvoiceView.this,list1);
            recyclerView.setAdapter(tcAdapter);

            if (res == null){
                Toast.makeText(InvoiceView.this, "Terms & Conditions Error", LENGTH_SHORT).show();
            }

        }
    }


    private class run1 {
        @SuppressLint("SetTextI18n")
        public run1(String p_name, String i_date, String t_end_date, String t_start_date, int u_children, int u_adults, int u_child_cost, int u_adult_cost, String p_type, int t_cost, String request, String status) {

            int TC1 = u_adults*u_adult_cost;
            int TC2 = u_children*u_child_cost;

            iDate.setText(i_date);

            pName.setText(p_name);

            pType.setText(p_type);

            status1.setText(status);

            sDate.setText(t_start_date);

            eDate.setText(t_end_date);

            request1.setText(request);

            if (st.getText().equals("")){
                list1.clear();
                Getdata1 getdata1 = new Getdata1();
                getdata1.execute();
            }else{
                String IData = iDate.getText().toString();
                String Pname = pName.getText().toString();
                String Ptype = pType.getText().toString();
                String Status3 = status1.getText().toString();
                String Sdate = sDate.getText().toString();
                String Edate = eDate.getText().toString();
                String Request3 = request1.getText().toString();
                String Q1 = q1.getText().toString();
                String Q2 = q2.getText().toString();
                String C1 = c1.getText().toString();
                String C2 = c2.getText().toString();
                String Tc1 = tc1.getText().toString();
                String Tc2 = tc2.getText().toString();
                String ST = st.getText().toString();

                Intent intent = new Intent(InvoiceView.this, InvoiceViewPDF.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("IData",IData);
                intent.putExtra("Pname",Pname);
                intent.putExtra("Ptype",Ptype);
                intent.putExtra("Status3",Status3);
                intent.putExtra("Sdate",Sdate);
                intent.putExtra("Edate",Edate);
                intent.putExtra("Request3",Request3);
                intent.putExtra("Q1",Q1);
                intent.putExtra("Q2",Q2);
                intent.putExtra("C1",C1);
                intent.putExtra("C2",C2);
                intent.putExtra("Tc1",Tc1);
                intent.putExtra("Tc2",Tc2);
                intent.putExtra("ST",ST);
                intent.putExtra("User",User);
                intent.putExtra("Email",Email);
                intent.putExtra("I_ID",I_ID);
                intent.putExtra("PType",PType);
                intent.putExtra("T_C",T_C);
                InvoiceView.this.startActivity(intent);
                finish();
            }

            q1.setText(""+u_adults);

            q2.setText(""+u_children);

            c1.setText(""+u_adult_cost);

            c2.setText(""+u_child_cost);

            tc1.setText(""+TC1);

            tc2.setText(""+TC2);

            st.setText(""+t_cost);

        }
    }

}