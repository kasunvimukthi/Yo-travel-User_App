package com.yo.test.Invoice;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yo.test.AdapterData.TCAdapterData;
import com.yo.test.Conn;
import com.yo.test.DrawerMain;
import com.yo.test.Login;
import com.yo.test.MainActivity;
import com.yo.test.R;
import com.yo.test.adaptor.TCAdapter;
import com.yo.test.fragment.DrawerActivity2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InvoiceViewPDF extends AppCompatActivity {

    String reciver, email, invoiceNo, iDate, pName, pType, status1, sDate, eDate, d1, d2, q1, q2, c1, c2, tc1, tc2, st, request1;

    WebView webView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view_pdf);

        webView = findViewById(R.id.webView2);
        button = findViewById(R.id.button15);

        Intent i = getIntent();
        String IData = i.getStringExtra("IData");
        String Pname = i.getStringExtra("Pname");
        String Ptype = i.getStringExtra("Ptype");
        String Status3 = i.getStringExtra("Status3");
        String Sdate = i.getStringExtra("Sdate");
        String Edate = i.getStringExtra("Edate");
        String Request3 = i.getStringExtra("Request3");
        String Q1 = i.getStringExtra("Q1");
        String Q2 = i.getStringExtra("Q2");
        String C1 = i.getStringExtra("C1");
        String C2 = i.getStringExtra("C2");
        String Tc1 = i.getStringExtra("Tc1");
        String Tc2 = i.getStringExtra("Tc2");
        String ST = i.getStringExtra("ST");
        String User = i.getStringExtra("User");
        String Email = i.getStringExtra("Email");
        String I_ID = i.getStringExtra("I_ID");

        iDate = IData;
        pName = Pname;
        pType = Ptype;
        status1 = Status3;
        sDate = Sdate;
        eDate = Edate;
        request1 = Request3;
        q1 = Q1;
        q2 = Q2;
        c1 = C1;
        c2 = C2;
        tc1 = Tc1;
        tc2 = Tc2;
        st = ST;
        reciver = User;
        email = Email;
        invoiceNo = I_ID;

        run1();

        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(50);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = InvoiceViewPDF.this;
                PrintManager printManager = (PrintManager) InvoiceViewPDF.this.getSystemService(context.PRINT_SERVICE);
                PrintDocumentAdapter printDocumentAdapter = null;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                    printDocumentAdapter = webView.createPrintDocumentAdapter();
                }
                String JobName = getString(R.string.app_name) +"Invoice";
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                    PrintJob printJob = printManager.print(JobName,printDocumentAdapter,new PrintAttributes.Builder().build());
                }
            }
        });

    }

    private void run1 (){

            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "\n" +
                    "<body>\n" +
                    "<div style=\"font-size: 30px;color: #666; line-height: 3; font-weight: bold;\">INVOICE</div>\n" +
                    "    \n" +
                    "    <table style=\"line-height: 1.5; font-size: 15px;\">\n" +
                    "        <tr>\n" +
                    "            <td style=\"font-weight: bold;\" width=\"350px\">Yo-travel(PVT)Ltd</td>\n" +
                    "            <td>Reciver : Mrs/Ms. "+reciver+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"350px\">Mobile : 076 575 6616</td>\n" +
                    "            <td>E-mail : "+email+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Address : 267/2,</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Ihala biyanwila, Kadawatha</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "\n" +
                    "    <div></div>\n" +
                    "\n" +
                    "    \n" +
                    "    <table style=\"line-height: 1.5; font-size: 15px;\">\n" +
                    "        <tr>\n" +
                    "            <td width=\"350px\">Invoice No : #"+invoiceNo+"</td>\n" +
                    "            <td >Payment Type : "+pType+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"350px\">Invoice Date : "+iDate+"</td>\n" +
                    "            <td>Status : "+status1+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Package Name : "+pName+"</td>\n" +
                    "        </tr>\n" +
                    "    <div></div>\n" +
                    "\n" +
                    "        <tr>\n" +
                    "            <td>Start Date : "+sDate+"</td>\n" +
                    "\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>End Date   : "+eDate+"</td>\n" +
                    "\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "\n" +
                    "\n" +
                    "    <div style=\"border-bottom-style: groove;\"></div>\n" +
                    "<div></div>\n" +
                    "    <table style=\"line-height: 1.5; font-size: 15px;  \">\n" +
                    "        <thead>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td></td>\n" +
                    "        </tr>\n" +
                    "            <tr style=\"font-weight: bold; background-color: lightblue;\">\n" +
                    "                <th width=\"250px\" >Description</th>\n" +
                    "                <th width=\"100px\" style=\"text-align: center; \">Quntity</th>\n" +
                    "                <th width=\"150px\" style=\"text-align: center; \">Cost for One Child / Adult</th>\n" +
                    "                <th width=\"125px\" style=\"text-align: center; \">Total Cost (Rs.)</th>\n" +
                    "\n" +
                    "            </tr>\n" +
                    "\n" +
                    "        </thead>\n" +
                    "        <tbody>\n" +
                    "            <tr style=\"background-color: lightgrey;\">\n" +
                    "            <td width=\"250px\" >Number of Child</td>\n" +
                    "            \n" +
                    "\n" +
                    "                <td width=\"100px\" style=\"text-align: right; text-align: center; \">"+q2+"</td>\n" +
                    "                <td width=\"150px\" style=\"text-align: right;\">"+c2+"</td>\n" +
                    "               \n" +
                    "                <td width=\"125px\" style=\"text-align: right;\">"+tc2+"</td>\n" +
                    "               \n" +
                    "            </tr>\n" +
                    "            <tr style=\"background-color: lightgrey;\">\n" +
                    "                <td>Number of Adults</td>\n" +
                    "                <td style=\"text-align: right; text-align: center; \">"+q1+"</td>\n" +
                    "                <td style=\"text-align: right;\">"+c1+"</td>\n" +
                    "                \n" +
                    "                <td width=\"125px\" style=\"text-align: right;\">"+tc1+"</td>\n" +
                    "\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-size: 13px; background-color: lightblue;\">\n" +
                    "                <td style=\" text-align: center; font-weight: bold;\" width=\"500px\">Sub Total</td>\n" +
                    "                <td></td>\n" +
                    "                <td></td>\n" +
                    "                <td style=\" text-align: right;\" width=\"125px\">"+st+"</td>\n" +
                    "\n" +
                    "            </tr>\n" +
                    "        </tbody>\n" +
                    "    </table>\n" +
                    "    <h4>Request</h4>\n" +
                    "   \n" +
                    "    \n" +
                    "    <div></div>\n" +
                    "\n" +
                    "    <div style=\"border-bottom-style: groove;\"></div>\n" +
                    "\n" +
                    "    <h4>Terms and Coditions</h4>\n" +
                    "\n" +
                    "    <style>\n" +
                    "        p{\n" +
                    "            font-size: 12px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "        <p>* You can cancel this invoive up to 7 days before trvel departure.</p>\n" +
                    "        <p>* You can cancel this invoive up to 7 days before trvel departure.</p>\n" +
                    "        <p>* You can cancel this invoive up to 7 days before trvel departure.</p>\n" +
                    "</body>\n" +
                    "</html>";

            webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);

    }
}