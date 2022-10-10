package com.yo.test.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yo.test.AdapterData.ActivityAdapterData;
import com.yo.test.AdapterData.ImageAdapterData;
import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.adaptor.ActivityAdapter;
import com.yo.test.adaptor.ImageAdapter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UpComingTabImages extends Fragment {

    private String P_ID;

    ImageAdapter imageAdapter;

    RecyclerView recyclerView;

    ArrayList<ImageAdapterData> list;

    public UpComingTabImages(String p_id) {
        this.P_ID = p_id;
    }

    Activity context1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming_tab_images, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.image_recycle);

        list = new ArrayList<>();

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        return view;
    }
    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";

        Blob img;
        byte b[];

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                    Statement st1 = con1.createStatement();
                    ResultSet rs1 = st1.executeQuery("SELECT * FROM `t_image` WHERE `T_ID` = "+ P_ID +"");

                    while (rs1.next()) {
                        img = rs1.getBlob(5);

                        b = img.getBytes(1, (int) img.length());
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                        list.add(new ImageAdapterData(bitmap));
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
            imageAdapter = new ImageAdapter(getContext(),list);
            recyclerView.setAdapter(imageAdapter);

            if (res == null){
                Toast.makeText(context1, "Image Error", LENGTH_SHORT).show();
            }

        }
    }

}