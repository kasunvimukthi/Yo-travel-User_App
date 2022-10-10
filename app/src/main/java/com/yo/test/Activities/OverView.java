package com.yo.test.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yo.test.Conn;
import com.yo.test.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OverView extends Fragment {

    View view;
    TextView textView1,textView2,textView3,textView4,textView5;
    String Name;
    WebView mWebView;

    Activity context1;
    public OverView(String name) {
        this.Name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context1 = getActivity();

        view = inflater.inflate(R.layout.fragment_over_view, container, false);

        textView1 = view.findViewById(R.id.textView96);
        textView2 = view.findViewById(R.id.textView100);
        textView3 = view.findViewById(R.id.textView101);
        textView4 = view.findViewById(R.id.textView102);
        textView5 = view.findViewById(R.id.textView103);
        mWebView = view.findViewById(R.id.webView4);

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        return view;
    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String A_Location = "";
        String Name1 = "";
        String A_summary = "";
        String A_Details = "";
        String A_Duration = "";
        String A_Map = "";
        String A_Best_Time = "";

        Blob img;
        byte b[];
        Bitmap bitmap;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st2 = con1.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM `t_activities` WHERE `A_Name` = '"+Name+"'");

                while (rs2.next()) {
                    A_Location = rs2.getString("A_Location").toString();
                    A_summary = rs2.getString("A_summary").toString();
                    A_Details = rs2.getString("A_Details").toString();
                    img = rs2.getBlob("A_Image");
                    A_Duration = rs2.getString("A_Duration").toString();
                    A_Map = rs2.getString("A_Map").toString();
                    A_Best_Time = rs2.getString("A_Best_Time").toString();

                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            textView1.setText(A_summary);
            textView2.setText(A_Location);
            textView3.setText(A_Duration);
            textView4.setText(A_Best_Time);
            textView5.setText(A_Details);
            mWebView.loadUrl(A_Map);

            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(true);
        }
    }

}