package com.yo.test.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.HighlightsAdapterData;
import com.yo.test.AdapterData.IncludesAdapterData;
import com.yo.test.AdapterData.TCAdapterData;
import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.adaptor.HighlightsAdapter;
import com.yo.test.adaptor.IncludesAdapter;
import com.yo.test.adaptor.TCAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class UpComingTabHighlights extends Fragment {

    View view;
    private String P_ID;
    TextView test,textView104,textView108,textView110;
    WebView mWebView;

    HighlightsAdapter highlightsAdapter;
    IncludesAdapter includesAdapter;
    TCAdapter tcAdapter;

    RecyclerView recyclerView, recyclerView2, recyclerView3;

    ArrayList<HighlightsAdapterData> list;
    ArrayList<IncludesAdapterData> list2;
    ArrayList<TCAdapterData> list3;

    ProgressBar progressBar;

    Activity context1;
    public UpComingTabHighlights(String p_id) {
        this.P_ID = p_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        context1 = getActivity();
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.fragment_up_coming_tab, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView5);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView6);
        recyclerView2.setVisibility(View.GONE);
        recyclerView2.setNestedScrollingEnabled(false);

        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView7);
        recyclerView3.setVisibility(View.GONE);
        recyclerView3.setNestedScrollingEnabled(false);

        progressBar = view.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);

        test = view.findViewById(R.id.textView13);
        textView104 = view.findViewById(R.id.textView104);
        textView108 = view.findViewById(R.id.textView108);
        textView110 = view.findViewById(R.id.textView110);
        mWebView = view.findViewById(R.id.packageMap);

        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        Getdata2 getdata2 = new Getdata2();
        getdata2.execute();

        Getdata3 getdata3 = new Getdata3();
        getdata3.execute();

        Getdata4 getdata4 = new Getdata4();
        getdata4.execute();

        return view;

    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String details = "";
        String location = "";
        String start = "";
        String end = "";
        String map = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `package` WHERE `Travel_ID` = "+ P_ID +"");

                if (rs1.next()) {
                    details = rs1.getString(5).toString();
                    start = rs1.getString(12).toString();
                    end = rs1.getString(13).toString();
                    location = rs1.getString(10).toString();
                    map = rs1.getString(11).toString();
                }

            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            test.setText(details);
            textView104.setText(start);
            textView108.setText(end);
            textView110.setText(location);
            mWebView.loadUrl(map);

            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(true);
        }
    }

    private class Getdata2 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String Data1 = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `t_highlights` WHERE `T_ID` = "+ P_ID +"");

                while (rs1.next()) {
                    Data1 = rs1.getString(3).toString();

                    list.add(new HighlightsAdapterData(Data1));

                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager layoutManager = new  LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            highlightsAdapter = new HighlightsAdapter(getContext(),list);
            recyclerView.setAdapter(highlightsAdapter);

            if (res == null){
                Toast.makeText(context1, "Highlights Error", LENGTH_SHORT).show();
            }

        }
    }

    private class Getdata3 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String Data1 = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `t_includes` WHERE `T_ID` = "+ P_ID +"");

                while (rs1.next()) {
                    Data1 = rs1.getString(3).toString();

                    list2.add(new IncludesAdapterData(Data1));

                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager layoutManager2 = new  LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView2.setLayoutManager(layoutManager2);
            includesAdapter = new IncludesAdapter(getContext(),list2);
            recyclerView2.setAdapter(includesAdapter);

            if (res == null){
                Toast.makeText(context1, "Includes Error", LENGTH_SHORT).show();
            }

        }
    }

    private class Getdata4 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String Data1 = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `t_conditions` WHERE `T_ID` = "+ P_ID +"");

                while (rs1.next()) {
                    Data1 = rs1.getString(3).toString();

                    list3.add(new TCAdapterData(Data1));

                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager layoutManager3 = new  LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView3.setLayoutManager(layoutManager3);
            tcAdapter = new TCAdapter(getContext(),list3);
            recyclerView3.setAdapter(tcAdapter);

            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.VISIBLE);
            recyclerView3.setVisibility(View.VISIBLE);

            if (res == null){
                Toast.makeText(context1, "Terms & Conditions Error", LENGTH_SHORT).show();
            }

        }
    }
}