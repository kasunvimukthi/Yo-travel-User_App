package com.yo.test.Invoice;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mysql.jdbc.PreparedStatement;
import com.yo.test.Conn;
import com.yo.test.R;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UploadRecipt extends AppCompatActivity {

    ConstraintLayout constraintLayout1, constraintLayout2;
    TextView textView, textView2;
    ImageView mImageView;
    FloatingActionButton mFloatingActionButton;

    private static final int REQUEST_IMAGE = 1;

    String I_ID, images;
    String file_path=null;

    byte[] bytes;
    private Object Activity;

    File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipt);

        Intent i = getIntent();
        String i_id = i.getStringExtra("I_ID");

        I_ID = i_id;

        constraintLayout1 = findViewById(R.id.layout1);
        constraintLayout2 = findViewById(R.id.layout2);
        textView = findViewById(R.id.textView82);
        textView2 = findViewById(R.id.textView83);
        mImageView = findViewById(R.id.imageView6);

                mFloatingActionButton = findViewById(R.id.floatingActionButton);

        constraintLayout2.setVisibility(View.VISIBLE);
        constraintLayout1.setVisibility(View.GONE);

        Run run = new Run();
        run.execute();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        textView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Run3 run3 = new Run3();
                run3.execute();

                Run2 run2 = new Run2();
                run2.execute();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    String filePath = getRealPathFromUri(data.getData(),UploadRecipt.this);
                    this.file_path=filePath;
                    file = new File(filePath);
                    file = new File(filePath);
                    textView2.setText(""+file.getName());

                }
            }
        }
    }

    public String getRealPathFromUri(Uri uri, android.app.Activity activity){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, proj, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int id = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }

    private class Run extends AsyncTask<Void, Void, String> {

        String res = "";
        Blob img;
        byte b[];

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM `payment` WHERE `I_ID` = '"+I_ID+"' ");

                if (rs.next()) {
                    img = rs.getBlob("Payment_Slip");

                    b = img.getBytes(1,(int)img.length());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b,0, b.length);

                    mImageView.setImageBitmap(bitmap);

                    if (img.length() != 0){
                        result = "image";
                    }else{
                        result = "";
                    }

                }
                else{
                    String quary = "INSERT INTO `payment`(`I_ID`) VALUES ('" + I_ID + "')";
                    Statement st1 = con.createStatement();
                    st1.executeUpdate(quary);

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

                if (result.equals("image")){
                constraintLayout2.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                constraintLayout1.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);
            }else{
                constraintLayout1.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                constraintLayout2.setVisibility(View.GONE);
            }

        }
    }

    private class Run2 extends AsyncTask<Void, Void, String> {

        String res = "";

        String imageup = textView2.getText().toString();
        FileInputStream fs=null;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                PreparedStatement ps=null;
                PreparedStatement ps2=null;
                fs=new FileInputStream(file);

                ps = (PreparedStatement) con.prepareStatement("UPDATE payment SET `Payment_Slip` =? WHERE I_ID =?");
                ps.setBinaryStream(1,fs,(int) file.length());
                ps.setString(2,I_ID);

                String result = "";

                ps.executeUpdate();


                    ps2 = (PreparedStatement) con.prepareStatement("UPDATE invoice SET `Status` =? WHERE Invoice_Number =?");
                    ps2.setString(1,"Proccessing");
                    ps2.setString(2,I_ID);

                    ps2.executeUpdate();


                        result = "Upload";


                res = result;
            }catch (Exception e){
                e.printStackTrace();
                res = e.toString();
            }

            return res;
        }
        @Override
        protected void onPostExecute(String result) {

            if (result.equals("Upload")){
                onBackPressed();
                Toast.makeText(UploadRecipt.this, "Image Uploaded", LENGTH_SHORT).show();
            }else {
                Toast.makeText(UploadRecipt.this, "Something Went Wrong", LENGTH_SHORT).show();
            }

        }
    }

    private class Run3 extends AsyncTask<Void, Void, String> {

        String res = "";
        String T_ID = "";
        String T_Name = "";
        String T_Start_Date = "";
        String No_of = "";
        String result = "";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM `invoice` WHERE `Invoice_Number` = '"+I_ID+"' ");

                while (rs.next()) {
                    T_ID = rs.getString("T_ID");

                    Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery("SELECT * FROM `package` WHERE `Travel_ID` = '"+T_ID+"' ");

                    while (rs1.next()) {
                        T_Name = rs1.getString("T_Name");
                        T_Start_Date = rs1.getString("T_Start_Date");

                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery("SELECT * FROM prediction WHERE P_Start_Date = '"+T_Start_Date+"' && P_ID ='"+T_ID+"'");

                        if (rs2.next()) {
                        }else{
                            No_of = "0";
                            String quary3 = "INSERT INTO prediction (P_ID,P_Name,P_Start_Date,No_of_pacenger) VALUES('"+T_ID+"','"+T_Name+"','"+T_Start_Date+"','"+No_of+"')";
                            Statement st3 = con.createStatement();
                            st3.executeUpdate(quary3);
                        }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

    }

}