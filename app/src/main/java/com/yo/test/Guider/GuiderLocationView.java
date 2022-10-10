package com.yo.test.Guider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yo.test.Conn;
import com.yo.test.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GuiderLocationView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double lat,lng;

    boolean locationPermission = false;
    Location myLocation = null;
    Location myUpdatedLocation = null;
    boolean AnimationStatus = false;

    private final static int LOCATION_REQUEST_CODE = 23;

    SharedPreferences sharedPreferences;

    private static final String SHARED_NAME = "my";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_U_ID = "";

    String User;
    String Email;
    String U_ID;

    TextView text1, text2;

    static Marker GuiderMarker, MyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider_location_view);
        requestPermision();

        sharedPreferences = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        User = sharedPreferences.getString(KEY_NAME,null);
        Email = sharedPreferences.getString(KEY_EMAIL,null);
        U_ID = sharedPreferences.getString(KEY_U_ID,null);

        text1 = findViewById(R.id.textView27);
        text2 = findViewById(R.id.textView28);

    }

    //to get user location
    private void getMyLocation() {
        LatLng latlng = new LatLng(7.0047,79.9542);
        MyLocation = mMap.addMarker(new MarkerOptions().position(latlng).title("My Location"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                latlng, 17f);
        mMap.animateCamera(cameraUpdate);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                if (AnimationStatus) {
                    myUpdatedLocation = location;
                } else {
                    myLocation = location;
                    myUpdatedLocation = location;
                    LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                    MyLocation.setPosition(latlng);

                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getMyLocation();

        lat = Double.valueOf(text1.getText().toString());
        lng = Double.valueOf(text2.getText().toString());

        LatLng sydney = new LatLng(lat, lng);
        GuiderMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Guider"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12F));



        Thread thread = new Thread(){
            @Override
            public void run(){
                while (!isInterrupted()){
                    try {
                        Thread.sleep(500);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Getdata1 getdata1 = new Getdata1();
                                getdata1.execute();

                                lat = Double.valueOf(text1.getText().toString());
                                lng = Double.valueOf(text2.getText().toString());

                                LatLng sydney = new LatLng(lat, lng);
                                GuiderMarker.setPosition(sydney);


                            }
                        });
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };


        thread.start();

    }

    private class Getdata1 extends AsyncTask<Void, Void, String> {

        Integer Loca;
        String res;
        String P_ID;
        String G_ID;
        Double Lati;
        Double Long;
        String I_Date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection(Conn.url, Conn.user, Conn.pass);

                String result = "";

                Statement st1 = con1.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM `invoice` WHERE `User_ID` = "+ U_ID +" && `Status` = 'Full Paid' && 'T_end_date' >= '"+I_Date+"' ORDER BY `T_end_date` ASC LIMIT 1");

                if (rs1.next()) {
                    P_ID = rs1.getString(3);
                    Loca = rs1.getInt(18);

                        Statement st2 = con1.createStatement();
                        ResultSet rs2 = st2.executeQuery("SELECT * FROM `guider_alocate` WHERE `P_ID` = "+ P_ID +"");

                        if (rs2.next()) {
                            G_ID = rs2.getString(2);
                            Statement st3 = con1.createStatement();
                            ResultSet rs3 = st3.executeQuery("SELECT * FROM `guider` WHERE `ID` = "+ G_ID +"");

                            while (rs3.next()) {
                                Lati = rs3.getDouble(6);
                                Long = rs3.getDouble(7);

                            }
                        }else{
                            result = "Guider isn't Allocate Yet";
                        }


                }else{
                    result = "Please Book Tour Package";
                    }
                res = result;
            }catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("")) {
                Toast.makeText(GuiderLocationView.this, "" + result, Toast.LENGTH_SHORT).show();
            }else{
                text1.setText(Lati.toString());
                text2.setText(Long.toString());
            }
        }
    }

    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            LocationstatusCheck();
            locationPermission = true;
            //init google map fragment to show map.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationstatusCheck();
                    //if permission granted.
                    locationPermission = true;
                    //init google map fragment to show map.
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                    // getMyLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    public void LocationstatusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}