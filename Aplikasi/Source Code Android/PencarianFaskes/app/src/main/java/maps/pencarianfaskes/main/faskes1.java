package maps.pencarianfaskes.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maps.pencarianfaskes.R;
import maps.pencarianfaskes.control.control;

/**
 * Created by RG 7 on 15/04/2017.
 */

public class faskes1 extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{


    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    //BitmapDescriptor marker;
    protected GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    String kecamatanvalue, jenisvalue;
    Spinner spinnerkecamatan, spinnerjenis;
    List<String> kecamatan;
    List<String> jenis;
    HashMap<String, Node> markers ;
    CoordinatorLayout view ;
    Snackbar sn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_faskes1);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_seach);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view = (CoordinatorLayout) findViewById(R.id.searchcoordinatorLayout);

        markers = new HashMap<>();

        //marker = BitmapDescriptorFactory.fromResource(R.mipmap.marker);
        kecamatan = new ArrayList<String>();
        kecamatan.add("Semua Kecamatan");
        jenis = new ArrayList<String>();


        initApi();
        new SearchKecamatan().execute();
        new SearchJenis().execute();


        spinnerkecamatan = (Spinner) findViewById(R.id.spinner);
        spinnerjenis = (Spinner) findViewById(R.id.spinner2);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.search_map);
        mMapFragment.getMapAsync(this);

        FloatingActionButton myLocation = (FloatingActionButton) findViewById(R.id.location);
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initApi();
                mGoogleApiClient.connect();
                if(sn != null) sn.dismiss();
            }
        });

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.process);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                if(sn != null) sn.dismiss();

                SearchData data = new SearchData();
                data.setKecamatan(kecamatanvalue);
                data.setJenis(jenisvalue);
                data.execute();

            }
        });
        spinnerkecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kecamatanvalue = parent.getItemAtPosition(position).toString();
//                SearchJenis jns = new SearchJenis();
//                jns.setName(kecamatanvalue);
//                jns.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerjenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisvalue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class SearchJenis extends AsyncTask<Void, Void, JSONArray>{

        ProgressDialog dialog = new ProgressDialog(faskes1.this);

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

            try {
                JSONObject json = control.executeHttpPost("http://rofiimron.web44.net/android/viewjenis.php", post);
                jarray = json.getJSONArray("jenis");

            }
            catch (Exception e) {
                Log.e("Error ", e.getMessage());
            }
            return jarray;
        }
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(jsonArray != null){
                try {
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        addToList(json.getString("jenis"), 1);
                    }
                    addToSpinner(1);
                } catch (JSONException e) {
                    Log.e("Error ", e.getMessage());
                }
            }
            dialog.dismiss();
        }
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading...");
            dialog.show();
        }
    }

    private class SearchData extends AsyncTask<Void, Void, JSONArray>{

        ProgressDialog dialog = new ProgressDialog(faskes1.this);
        private String kecamatan, jenis;

        public void setKecamatan(String kecamatan) {
            this.kecamatan = kecamatan;
        }

        public void setJenis(String jenis) {
            this.jenis = jenis;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("kecamatan", kecamatan));
            post.add(new BasicNameValuePair("jenis", jenis));
            spinnerkecamatan = (Spinner) findViewById(R.id.spinner);
            spinnerjenis = (Spinner) findViewById(R.id.spinner2);
            try {
                JSONObject json = control.executeHttpPost("http://rofiimron.web44.net/android/carifaskes1.php", post);
                jarray = json.getJSONArray("fk1");
            }
            catch (Exception e) {
                Log.e("Error ", e.getMessage());
            }
            return jarray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            double lat; double lng;

            if(jsonArray != null){
                try {
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        lat = Double.parseDouble(json.getString("latitude"));
                        lng = Double.parseDouble(json.getString("longitude"));
                        createMarker(lat,lng, json.getString("nama"), json.getString("telepon"), json.getString("alamat"), json.getString("id"));
                    }
                } catch (JSONException e) {
                    Log.e("Error ", e.getMessage());
                }
            }
            dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading...");
            dialog.show();
        }
    }

    private class SearchKecamatan extends AsyncTask<Void, Void, JSONArray>{

        ProgressDialog dialog = new ProgressDialog(faskes1.this);

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(jsonArray != null){
                try {
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        addToList(json.getString("kecamatan"), 0);
                    }
                    addToSpinner(0);
                } catch (JSONException e) {
                    Log.e("Error ", e.getMessage());
                }
            }
            dialog.dismiss();
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            try {
                JSONObject json = control.getJSONfromURL("http://rofiimron.web44.net/android/viewkecamatan.php");
                jarray = json.getJSONArray("kecamatan");
            } catch (JSONException e) {
                Log.e("Error ", e.getMessage());
            }
            return jarray;
        }

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Loading...");
            dialog.show();
        }
    }

    private void addToList(String value, int type){
        if(type == 0){
            kecamatan.add(value);
        }else if(type == 1){
            this.jenis.add(value);
        }
    }

    private void addToSpinner(int type){
        if(type==0){
            ArrayAdapter<String> spinnKecamatan = new ArrayAdapter<String>(this,R.layout.search_content, kecamatan);
            spinnerkecamatan.setAdapter(spinnKecamatan);
        }else if(type==1){
            ArrayAdapter<String> spinnJenis = new ArrayAdapter<String>(this, R.layout.search_content, jenis);
            spinnerjenis.setAdapter(spinnJenis);
        }
        kecamatan = new ArrayList<String>();
        jenis = new ArrayList<String>();
        jenis.add("Semua Fasilitas Kesehatan");
    }

    private void initApi(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    public void Mylocation(double latitude, double longitude){
        try {
            LatLng latLng = new LatLng(latitude, longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(15)
                    .bearing(360)
                    .tilt(15)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }catch (Exception e){
            Log.e("Error " , e.getMessage());
        }
    }

    public void createMarker(double latitude, double longitude, String nama, String telepon, String alamat, String id){
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        markers.put(String.valueOf(latitude)+ "_" + String.valueOf(longitude), new Node(id, nama,telepon, alamat));
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(sn != null) sn.dismiss();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                sn = Snackbar
                        .make(view, getInfoMarker(marker, "nama") + "\n" + getInfoMarker(marker, "telepon") + "\n" + getInfoMarker(marker, "alamat") , Snackbar.LENGTH_INDEFINITE)
                        .setAction("Rute", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(MainActivity.this, getId(marker), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), jalur.class);
                                Bundle b = new Bundle();
                                b.putString("id", getInfoMarker(marker, "id"));
                                b.putString("nama", getInfoMarker(marker, "nama"));
                                b.putString("alamat", getInfoMarker(marker, "alamat"));
                                b.putString("lat", getInfoMarker(marker , "lat"));
                                b.putString("lng", getInfoMarker(marker, "lng"));
                                i.putExtra("parse", b);
                                startActivity(i);
                            }
                        })
                ;
                sn.setActionTextColor(Color.BLACK);
                View snackbarView = sn.getView();
                snackbarView.setBackgroundColor(Color.WHITE);
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.BLACK);
                textView.setMaxLines(3);
                sn.show();
                return false;
            }
        });
    }

    private String getInfoMarker(Marker marker, String type){
        String result = "" ;
        String getMarker = String.valueOf(marker.getPosition().latitude)  + "_" +  String.valueOf(marker.getPosition().longitude);
        if(type.equals("id")){
            result = markers.get(getMarker).getId();
        }else if(type.equals("nama")){
            result = markers.get(getMarker).getNama();
        }else if(type.equals("telepon")){
            result = markers.get(getMarker).getTelepon();
        }else if(type.equals("alamat")){
            result = markers.get(getMarker).getAlamat();
        }else if(type.equals("lat")){
            result = String.valueOf(marker.getPosition().latitude);
        }else if(type.equals("lng")){
            result = String.valueOf(marker.getPosition().longitude);
        }
        return result;
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Error ", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        Mylocation(currentLatitude, currentLongitude);
    }

}
