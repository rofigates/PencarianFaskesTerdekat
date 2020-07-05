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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import maps.pencarianfaskes.R;
import maps.pencarianfaskes.control.control;

/**
 * Created by RG 7 on 15/04/2017.
 */

public class faskes2 extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    BitmapDescriptor marker;
    protected GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    HashMap<String, Node> markers ;
    RelativeLayout view ;
    Snackbar sn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faskes2);

        mMapFragment = SupportMapFragment.newInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = (RelativeLayout) findViewById(R.id.faskes2coordinator);

        initApi();
        markers = new HashMap<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sn != null) sn.dismiss();
                initApi();
                mGoogleApiClient.connect();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        new getData().execute();
    }

    private class getData extends AsyncTask<Void, Void, JSONArray> {

        ProgressDialog dialog = new ProgressDialog(faskes2.this);

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            try {
                JSONObject json = control.getJSONfromURL("http://rofiimron.web44.net/android/carifaskes2.php");
                jarray = json.getJSONArray("fk2");
            } catch (JSONException e) {
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

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
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
        Toast.makeText(faskes2.this, "Connection Suspended try again later", Toast.LENGTH_SHORT).show();


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


    public void createMarker(double latitude, double longitude, String nama, String telepon, String alamat, String id){
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        markers.put(String.valueOf(latitude)+ "_" + String.valueOf(longitude), new Node(id, nama, telepon, alamat));
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


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        Mylocation(currentLatitude, currentLongitude);
    }
}

