package com.yo.test.Activities;

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

import com.yo.test.Conn;
import com.yo.test.R;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ActivityImage extends Fragment {

    private String Name;

    ImageAdapter1 imageAdapter;

    RecyclerView recyclerView;

    ArrayList<ImageAdapterData1> list;

    public ActivityImage(String name) {
        this.Name = name;
    }
    Activity context1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity_image, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.A_Image_re);

        list = new ArrayList<>();

        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();

        return view;
    }

    private class Getdata1 extends AsyncTask<Void, Void, Void> {

        String res = "";
        String ID = "";

        Blob img;
        byte b[];

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st1 = con1.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `t_activities` WHERE `A_Name` = '"+Name+"'");

                if (rs1.next()) {
                    ID = rs1.getString("ID");

                    Statement st2 = con1.createStatement();
                    ResultSet rs2 = st2.executeQuery("SELECT * FROM `t_image` WHERE `AC_ID` = '"+ID+"'");

                    while (rs2.next()) {

                        img = rs2.getBlob("T_Image");

                        b = img.getBytes(1, (int) img.length());
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                        list.add(new ImageAdapterData1(bitmap));
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
            imageAdapter = new ImageAdapter1(getContext(),list);
            recyclerView.setAdapter(imageAdapter);

            if (res == null){
                Toast.makeText(context1, "Image Error", LENGTH_SHORT).show();
            }

        }
    }

}