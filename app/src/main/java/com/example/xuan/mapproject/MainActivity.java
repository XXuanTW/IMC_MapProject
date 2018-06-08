package com.example.xuan.mapproject;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {
    private static final int READ_CONTACTS_REQUEST = 1;
    private GoogleMap mMap;
    Button serach;
    EditText SEText;
    String [] MarkerData;
    String [] MarkerUrl;
    String MarkerInfo = "";
    String SerachInfo ="";
    String DataUrl = "http://www.oneimc.net/admin/markers/Test.php";
    int Infovalue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //權限
        getPermissionToReadUserContacts();
        //google mapFragment 設定
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        serach = (Button)findViewById(R.id.Search);
        SEText = (EditText)findViewById(R.id.SEText);
        mapFragment.getMapAsync(this);
        new TransTask().execute(DataUrl);

        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new TransTask().execute(DataUrl);
                LatLng HOME = new LatLng(23.8519384, 120.9107672);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HOME, 8.0f));
                SerachInfo = SEText.getText().toString();
                mMap.clear();
                new TransTask().execute(DataUrl);
            }
        });

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        MarkerInfo = marker.getTitle();

        for (int i = 0; i < Infovalue ; i++ ){
            if (MarkerInfo.equals(MarkerData[i])) {
                if (MarkerUrl[i].equals("0") || MarkerUrl[i].equals("")) {
                    Uri uri = Uri.parse("http://www.imc.org.tw");
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } else {
                    Uri uri = Uri.parse("http://" + MarkerUrl[i]);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }
            }
        }

    }

    class TransTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while (line != null) {
                    Log.d("HTTP", line);
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }
    }

    private void parseJSON(String s) {
        ArrayList<Transaction> trans = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(s);
            MarkerData = new String[array.length()];
            MarkerUrl = new String[array.length()];
            Infovalue = 0;

            for (int i=0; i<=array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String e_mobile = obj.getString("e_mobile");
                String e_position = obj.getString("e_position");
                String e_comp_web = obj.getString("e_comp_web");
                String e_photo = obj.getString("e_photo");
                String m_lat = obj.getString("m_lat");
                String m_lng = obj.getString("m_lng");
                String m_type = obj.getString("m_type");
                String e_comp_name = obj.getString("e_comp_name");
                String e_name = obj.getString("e_name");
                String m_address = obj.getString("m_address");
                String sql_title4 = obj.getString("sql_title4");
                Transaction t = new Transaction(e_mobile, e_position, e_comp_web, e_photo,m_lat,m_lng,m_type,e_comp_name,e_name,m_address,sql_title4);
                //標籤
                if (SerachInfo.equals("")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.valueOf(m_lat), Double.valueOf(m_lng)))
                            .title(e_name + "_" + e_comp_name)
                            .snippet(sql_title4));
                    trans.add(t);
                    MarkerData[Infovalue] = e_name + "_" + e_comp_name;
                    MarkerUrl[Infovalue] = e_comp_web;
                    Infovalue = Infovalue + 1;
                }else if(sql_title4.indexOf(SerachInfo)>=0){
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.valueOf(m_lat), Double.valueOf(m_lng)))
                            .title(e_name + "_" + e_comp_name)
                            .snippet(sql_title4));
                    trans.add(t);
                    MarkerData[Infovalue] = e_name + "_" + e_comp_name;
                    MarkerUrl[Infovalue] = e_comp_web;
                    Infovalue = Infovalue + 1;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//google 功能設定
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //GPS定位
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        //放大縮小
        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        double lat, lng;
//        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 設定定位資訊由 GPS提供
//        lat = location.getLatitude();  // 取得經度
//        lng = location.getLongitude(); // 取得緯度
//        LatLng HOME = new LatLng(lat, lng);
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HOME, 15.0f));

        LatLng HOME = new LatLng(23.8519384, 120.9107672);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HOME, 8.0f));
//        //標籤
//        mMap.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title("100"));
//        new TransTask().execute(Url);
        mMap.setOnInfoWindowClickListener(this);
    }

    //權限
    public void getPermissionToReadUserContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED){
            {
            //解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                Toast.makeText(this, "需要定位位置權限以及儲存權限才可以正常工作", Toast.LENGTH_SHORT).show();
            }
            //发起请求获得用户许可,可以在此请求多个权限
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET},
                    READ_CONTACTS_REQUEST);
            }
        }
    }


}
