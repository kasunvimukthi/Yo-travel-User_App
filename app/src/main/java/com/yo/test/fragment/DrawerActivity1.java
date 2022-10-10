package com.yo.test.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.DA1adapterData1;
import com.yo.test.AdapterData.DA1adapterData2;
import com.yo.test.AdapterData.DA1adapterData3;
import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.adaptor.DAactivity1Adaptor1;
import com.yo.test.adaptor.DAactivity1Adaptor2;
import com.yo.test.adaptor.DAactivity1Adaptor3;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DrawerActivity1 extends Fragment {
    Connection con;

    TextView textView;
    CardView mCardView;

    DAactivity1Adaptor1 dAactivity1Adaptor1;
    DAactivity1Adaptor2 dAactivity1Adaptor2;
    DAactivity1Adaptor3 dAactivity1Adaptor3;

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;

    ArrayList<DA1adapterData1> list;
    ArrayList<DA1adapterData2> list2;
    ArrayList<DA1adapterData3> list3;

    ProgressBar progressBar;
    ProgressDialog mProgressDialog;

    Activity context1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context1 = getActivity();
        View view = inflater.inflate(R.layout.activity_drawer1,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setVisibility(View.GONE);
        recyclerView2.setNestedScrollingEnabled(false);

        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
        recyclerView3.setVisibility(View.GONE);

        progressBar = view.findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        mProgressDialog = ProgressDialog.show(context1,"Please Wait","Data Loading...",true,false);

        mCardView = view.findViewById(R.id.cardView5);

        textView = view.findViewById(R.id.textView106);
        textView.setText(getCurrentTime());

        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        mCardView.setVisibility(View.GONE);
        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        Getdata2 getdata2 = new Getdata2();
        getdata2.execute();

        Getdata3 getdata3 = new Getdata3();
        getdata3.execute();


        return view;
    }

    private String getCurrentTime(){
        String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        String Date = new SimpleDateFormat("yyyy-MMMM-dd",Locale.getDefault()).format(new Date());
        String DateTime = ""+time+", On "+Date;
        return DateTime;
    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String name = "";
        String id = "";
        String pid = "";
        String status = "1";
        Blob img;
        byte b[];

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);


                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `no_of_travelers` WHERE `No_of_Travelers` > 5 ORDER BY `No_of_Travelers` ");

                while (rs1.next()) {
                    pid = rs1.getString(1).toString();

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM `package` WHERE Travel_ID = "+ pid +" && Status =" + status +"");

                while (rs.next()) {

                    name =  rs.getString(3).toString();
                    id =  rs.getString(1).toString();
                    img = rs.getBlob(4);

                    b = img.getBytes(1,(int)img.length());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b,0, b.length);


                    list.add(new DA1adapterData1(name,id,bitmap));

                }
                }
            }catch (Exception e){
                res = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager layoutManager = new  LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(layoutManager);
            dAactivity1Adaptor1 = new DAactivity1Adaptor1(getContext(),list);
            recyclerView.setAdapter(dAactivity1Adaptor1);

            if (res == null){
                Toast.makeText(context1, "Top Package List Error", LENGTH_SHORT).show();
            }

        }
    }

    private class Getdata2 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String name = "";
        String c_ID = "";
        Blob img;
        byte b[];

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM catogory");

                    while (rs.next()) {

                        c_ID = rs.getString(1).toString();
                        name =  rs.getString(2).toString();
                        img = rs.getBlob(3);

                        b = img.getBytes(1,(int)img.length());
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0, b.length);

                        list2.add(new DA1adapterData2(name,bitmap,c_ID));



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
            dAactivity1Adaptor2 = new DAactivity1Adaptor2(getContext(),list2);
            recyclerView2.setAdapter(dAactivity1Adaptor2);

            if (res == null){
                Toast.makeText(context1, "category list error", LENGTH_SHORT).show();
            }

        }
    }

    private class Getdata3 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String name = "";
        String location = "";
        String sDate = "";
        String eDate = "";
        String pStatus = "Active";
        String pid = "";
        String status = "1";
        String aSeat = "";

        int child = 0;
        int adult = 0;
        int seat = 0;

        Blob img;
        byte b[];

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM `package` WHERE Status =" + status +"");

                    while (rs.next()) {

                        pid =  rs.getString(1).toString();
                        name =  rs.getString(3).toString();
                        location =  rs.getString(10).toString();
                        sDate =  rs.getString(12).toString();
                        eDate =  rs.getString(13).toString();
                        seat = rs.getInt(14);
                        img = rs.getBlob(4);

                        b = img.getBytes(1,(int)img.length());
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0, b.length);

                        list3.add(new DA1adapterData3(name, pid, location, pStatus, sDate, eDate, bitmap));



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
            dAactivity1Adaptor3 = new DAactivity1Adaptor3(getContext(),list3);
            recyclerView3.setAdapter(dAactivity1Adaptor3);

            progressBar.setVisibility(View.GONE);
            mProgressDialog.dismiss();
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.VISIBLE);
            recyclerView3.setVisibility(View.VISIBLE);
            mCardView.setVisibility(View.VISIBLE);

            if (res == null){
                Toast.makeText(context1, "Something Went Wrong", LENGTH_SHORT).show();
            }

        }
    }





}

