package com.yo.test.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.AccomoAdapterData;
import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.adaptor.AccomoAdapter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class UpComingTabAccomo extends Fragment {

    private String P_ID;

    AccomoAdapter accomoAdapter;

    RecyclerView recyclerView;

    ArrayList<AccomoAdapterData> list;


    public UpComingTabAccomo(String p_id) {
        this.P_ID = p_id;
    }
    Activity context1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        context1 = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming_tab_accomo, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.accomoRecycle);

        list = new ArrayList<>();

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        return view;
    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String ID = "";
        String res = "";
        String Name = "";
        String Loc = "";
        String Sum = "";

        Blob img;
        byte b[];

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st2 = con1.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM `p_accommodation` WHERE `P_ID` = '"+ P_ID +"'");

                while (rs2.next()) {
                    ID = rs2.getString(3).toString();

                Statement st1 = con1.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `t_accommodation` WHERE `ID` = '"+ ID +"'");

                while (rs1.next()) {
                    Name = rs1.getString(2).toString();
                    Loc = rs1.getString(4).toString();
                    Sum = rs1.getString(3).toString();
                    img = rs1.getBlob(7);

                    b = img.getBytes(1, (int) img.length());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                    list.add(new AccomoAdapterData(Name, Loc, Sum, bitmap));
                }
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
            accomoAdapter = new AccomoAdapter(getContext(),list);
            recyclerView.setAdapter(accomoAdapter);

            if (res == null){
                Toast.makeText(context1, "Accomodation Error", LENGTH_SHORT).show();
            }

        }
    }
}