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
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import maps.pencarianfaskes.control.control;
import maps.pencarianfaskes.R;

/**
 * Created by badawi on 7/29/2016.
 */
public class CustomRute extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    protected GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    private ArrayList<LatLng> arrayPoints = null;
    PolylineOptions polylineOptions ;
    String distance;
    CoordinatorLayout coordinateLayout;
    Snackbar sn;
    double targetlat , targetlng, startlat, startlng;
    String id, startname ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.app_bar_rute);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("parse");
        startlat = Double.parseDouble(bundle.getString("startlat"));
        startlng = Double.parseDouble(bundle.getString("startlng"));
        startname = bundle.getString("startname");

        targetlat = Double.parseDouble(bundle.getString("lat"));
        targetlng = Double.parseDouble(bundle.getString("lng"));
        id = bundle.getString("id");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rute);

        setSupportActionBar(toolbar);
        coordinateLayout = (CoordinatorLayout) findViewById(R.id.rutecoordinatorLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(bundle.getString("nama"));
        getSupportActionBar().setSubtitle(bundle.getString("alamat"));


        arrayPoints = new ArrayList<LatLng>();
        initApi();




        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.rute_map);
        mMapFragment.getMapAsync(this);
        FloatingActionButton myLocation = (FloatingActionButton) findViewById(R.id.fab_rute);
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initApi();
                mGoogleApiClient.connect();
            }
        });

    }


    private void zoom(double lat, double lng){
        LatLng latLng = new LatLng(lat, lng);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .bearing(360)
                .tilt(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private void createMarker(double lat, double lng, int type){
        //Toast.makeText(Rute.this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
        //Toast.makeText(Rute.this, String.valueOf(lng), Toast.).show();
        try {
            if(type==0){
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(startname)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }else if(type==1){
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
            }
        }catch (Exception e){
            Log.e("Error ", e.getMessage());
        }
    }


    private class coordianate extends AsyncTask<Void, Void, JSONArray> {

        ProgressDialog dialog = new ProgressDialog(CustomRute.this);
        String id, lat, lng;

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setId(String id) {
            this.id = id;
        }



        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            double lat; double lng;
            //Toast.makeText(coordianate.this, jsonArray.length(), Toast.LENGTH_SHORT).show();
            if(jsonArray != null){
                try {
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        lat = Double.parseDouble(json.getString("lat"));
                        lng = Double.parseDouble(json.getString("lng"));
                        AddPoint(lat, lng);
                    }
                    DrawPolyline();
                    setDistance();
                } catch (JSONException e) {
                    Log.e("Error ", e.getMessage());
                }
            }
            dialog.dismiss();
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("id", id));
            post.add(new BasicNameValuePair("mylat", lat));
            post.add(new BasicNameValuePair("mylng", lng));

            JSONObject json = null;
            try {
                json = control.executeHttpPost("http://rofiimron.web44.net/algoritma/test.php", post);
                jarray = json.getJSONArray("path");
                distance = json.getString("distance");
            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
            }
            return jarray;
        }
    }

    private void setDistance(){
        sn = Snackbar.make(coordinateLayout, "Jarak = " + distance, Snackbar.LENGTH_INDEFINITE);
        sn.setActionTextColor(Color.BLACK);
        View snackbarView = sn.getView();
        snackbarView.setBackgroundColor(Color.WHITE);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        sn.show();
    }

    public void AddPoint(double lat, double lng){
        arrayPoints.add(new LatLng(lat, lng));
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
                    .zoom(17)
                    .bearing(360)
                    .tilt(15)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }catch (Exception e){
            Log.e("Error " , e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
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
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
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



    private void DrawPolyline(){
        try {
            if(arrayPoints==null){
            }else{
                polylineOptions.addAll(arrayPoints);
                polylineOptions.color(Color.BLUE);
                mMap.addPolyline(polylineOptions);
            }
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        polylineOptions = new PolylineOptions();
        createMarker(targetlat, targetlng, 1);
        createMarker(startlat, startlng, 0);
        zoom(startlat, startlng);
        coordianate co = new coordianate();
        co.setId(id);
        co.setLat(String.valueOf(startlat));
        co.setLng(String.valueOf(startlng));
        co.execute();
    }



    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        Mylocation(currentLatitude, currentLongitude);
    }




}
