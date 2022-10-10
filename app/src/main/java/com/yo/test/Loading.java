//
//
//package com.yo.test;
//
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import com.yo.test.Conn;
//        import android.content.Intent;
//        import android.os.AsyncTask;
//        import android.os.Bundle;
//        import android.os.Handler;
//        import android.view.View;
//        import android.view.Window;
//        import android.view.WindowManager;
//        import android.widget.Button;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import java.sql.Connection;
//        import java.sql.DriverManager;
//        import java.sql.ResultSet;
//        import java.sql.ResultSetMetaData;
//        import java.sql.Statement;
//
//public class Loading extends AppCompatActivity {
//
//    Button btnFetch,btnClear,firstbtn;
//    TextView txtData;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_loading);
//
//
//        txtData = (TextView) this.findViewById(R.id.txtData);
//        btnFetch = (Button) findViewById(R.id.btnFetch);
//        btnClear = (Button) findViewById(R.id.btnClear);
//        firstbtn = (Button) findViewById(R.id.page1);
//
//        firstbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Loading.this,Login.class);
//                startActivity(intent);
//            }
//        });
//        btnFetch.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                ConnectMySql connectMySql = new ConnectMySql();
//                connectMySql.execute("");
//            }
//        });
//        btnClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtData.setText("");
//            }
//        });
//    }
//
//    private class ConnectMySql extends AsyncTask<String, Void, String> {
//        String res = "";
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(Loading.this, "Please wait...", Toast.LENGTH_SHORT)
//                    .show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);
//                System.out.println("Databaseection success");
//
//                String result = "Database Conn Successful\n";
//                Statement st = con.createStatement();
//                ResultSet rs = st.executeQuery("select distinct Country from tblCountries");
//                ResultSetMetaData rsmd = rs.getMetaData();
//
//                while (rs.next()) {
//                    result += rs.getString(1).toString() + "\n";
//                }
//                res = result;
//            } catch (Exception e) {
//                e.printStackTrace();
//                res = e.toString();
//            }
//            return res;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            txtData.setText(result);
//        }
//    }
//}