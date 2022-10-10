package com.yo.test.fragment;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yo.test.AdapterData.InvoiceAdapterData;
import com.yo.test.Conn;
import com.yo.test.R;
import com.yo.test.adaptor.InvoiceActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DrawerActivity2 extends Fragment {

    InvoiceActivity invoiceActivity;

    RecyclerView recyclerView;

    ArrayList<InvoiceAdapterData> list1;

    ProgressBar progressBar;

    Activity context1;

    String U_ID;

    public DrawerActivity2(String u_id) {
        this.U_ID = u_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_drawer2,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
        recyclerView.setVisibility(View.GONE);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        list1 = new ArrayList<>();

//        Getdata1 getdata1 = new Getdata1();
//        getdata1.execute();

        return view;
    }

    private class Getdata1 extends AsyncTask<Void, Void, String> {

        String res = "";
        String P_name = "";
        String I_ID = "";
        String id = "";
        String status = "";
        String I_date = "";
        String I_S_date = "";

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM `invoice` WHERE User_ID = "+ U_ID + "");

                    while (rs.next()) {

                        I_ID =  rs.getString(1).toString();
                        id =  rs.getString(3).toString();
                        status =  rs.getString(17).toString();
                        I_date =  rs.getString(4).toString();
                        I_S_date =  rs.getString(7).toString();

                        Statement st1 = con.createStatement();
                        ResultSet rs1 = st1.executeQuery("SELECT * FROM `package` WHERE Travel_ID = "+ id + "");

                        while (rs1.next()) {

                            P_name =  rs1.getString(3).toString();

                            list1.add(new InvoiceAdapterData(P_name, I_ID, status, I_date, I_S_date));

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
            LinearLayoutManager layoutManager = new  LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            invoiceActivity = new InvoiceActivity(getContext(),list1);
            recyclerView.setAdapter(invoiceActivity);

            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

//            if (res.equals("")){
//                Toast.makeText(context1, "Your haven't book travel package", LENGTH_SHORT).show();
//            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        list1.clear();
        Getdata1 getdata1 = new Getdata1();
        getdata1.execute();
    }
}