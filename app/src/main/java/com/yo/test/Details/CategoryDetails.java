package com.yo.test.Details;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yo.test.AdapterData.CategoryAdapterData;
import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.adaptor.CategoryAdapter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDetails extends Fragment {

    CategoryAdapter categoryAdapter;

    RecyclerView recyclerView;

    ArrayList<CategoryAdapterData> list;

    ProgressBar progressBar;
    ProgressDialog mProgressDialog;

    Activity context1;

    TextView textView;

    private String id;
    private String C_name;

    public CategoryDetails(String c_id, String c_name) {
        this.id = c_id;
        this.C_name = c_name;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context1 = getActivity();
        View view = inflater.inflate(R.layout.activity_category_details,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView8);
        recyclerView.setVisibility(View.GONE);

        textView = view.findViewById(R.id.Cat_ID);
        textView.setText(C_name);

        mProgressDialog = ProgressDialog.show(context1,"Please Wait","Data Loading...",true,false);
        progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        list = new ArrayList<>();

        Getdata getdata = new Getdata();
        getdata.execute();

        return view;
    }

    private class Getdata extends AsyncTask<Void, Void, Void>{

        String res = "";
        String name = "";
        String s_date = "";
        String e_date = "";
        String summ = "";
        String p_id = "";
        String location = "";
        String status = "1";
        Blob img;
        byte b[];

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM `package` WHERE C_ID = "+ id +" && Status =" + status +"");

                    while (rs.next()) {

                        s_date =  rs.getString(12).toString();
                        e_date =  rs.getString(13).toString();
                        summ =  rs.getString(5).toString();
                        p_id =  rs.getString(1).toString();
                        location =  rs.getString(10).toString();

                        name =  rs.getString(3).toString();
                        img = rs.getBlob(4);

                        b = img.getBytes(1,(int)img.length());
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0, b.length);

                        list.add(new CategoryAdapterData(p_id,s_date, e_date,name, location,summ, bitmap));


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
            categoryAdapter = new CategoryAdapter(getContext(),list);
            recyclerView.setAdapter(categoryAdapter);

            progressBar.setVisibility(View.GONE);
            mProgressDialog.dismiss();
            recyclerView.setVisibility(View.VISIBLE);

            if (res == null){
                Toast.makeText(context1, "Top Package List Error", LENGTH_SHORT).show();
            }

        }



    }



}