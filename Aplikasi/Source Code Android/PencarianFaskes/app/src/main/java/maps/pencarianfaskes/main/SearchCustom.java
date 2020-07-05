package maps.pencarianfaskes.main;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maps.pencarianfaskes.R;
import maps.pencarianfaskes.control.AutoAdapter;
import maps.pencarianfaskes.control.AutoItems;
import maps.pencarianfaskes.control.control;

/**
 * Created by badawi on 7/27/2016.
 */
public class SearchCustom extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    //BitmapDescriptor marker;
    protected GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    String bankvalue, nominalvalue;
    Spinner spinnerbank, spinnernominal;
    List<String> bank;
    List<String> nominal;
    HashMap<String, Node1> markers ;
    CoordinatorLayout view ;
    Snackbar sn ;

    private List<AutoItems> Autolist ;
    AutoAdapter autoAdapter;
    AutoCompleteTextView autoCompleteTextView;
    TextInputLayout layouttext;
    String id_auto ;
    String startlat, startlng, startname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.search_custom);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_seach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Autolist = new ArrayList<AutoItems>();

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.start_location);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.addTextChangedListener(new Watcher(autoCompleteTextView));
        view = (CoordinatorLayout) findViewById(R.id.searchcoordinatorLayout);
        layouttext = (TextInputLayout) findViewById(R.id.layout_startlocation);
        new Gettsartlocation().execute();



        markers = new HashMap<>();

        //marker = BitmapDescriptorFactory.fromResource(R.mipmap.marker);
        bank = new ArrayList<String>();
        bank.add("Semua Kecamatan");
        nominal = new ArrayList<String>();


        initApi();
        new GetBank().execute();


        spinnerbank = (Spinner) findViewById(R.id.spinner);
        spinnernominal = (Spinner) findViewById(R.id.spinner2);

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
                autoCompleteTextView.clearFocus();
                mMap.clear();
                if(sn != null) sn.dismiss();

                SeachData data = new SeachData();
                data.setNama(bankvalue);
                data.setNominal(nominalvalue);
                data.execute();

                SearchstartLocation start = new SearchstartLocation();
                start.setId(id_auto);
                start.execute();

            }
        });
        spinnerbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                bankvalue = parent.getItemAtPosition(position).toString();
                SeachNominal nomina = new SeachNominal();
                nomina.setName(bankvalue);
                nomina.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnernominal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.clearFocus();
                nominalvalue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_auto = ((TextView) view.findViewById(R.id.id_auto)).getText().toString();
                String name_auto = ((TextView) view.findViewById(R.id.name_auto)).getText().toString();
                autoCompleteTextView.setText(name_auto);
            }
        });
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/
                if(!hasFocus){
                    autoCompleteTextView.clearFocus();
                }
            }
        });
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class Watcher implements TextWatcher {

        private View view;

        private Watcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()) {
                case R.id.start_location:
                    validate();
                    break;
            }
        }

        public void afterTextChanged(Editable editable) {

        }
    }

    private boolean validate() {
        if (autoCompleteTextView.getText().toString().trim().isEmpty() || autoCompleteTextView.getText().toString().trim().equals("")) {
            layouttext.setError("Isi data");
            requestFocus(autoCompleteTextView);
            Log.e("betul", autoCompleteTextView.getText().toString());
            return false;
        } else {
            Log.e("salah ", autoCompleteTextView.getText().toString());
            layouttext.setErrorEnabled(false);
        }
        return true;
    }





    private class Gettsartlocation extends AsyncTask<Void, Void, JSONArray>{

        ProgressDialog dialog = new ProgressDialog(SearchCustom.this);

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if(jsonArray != null){
                try {
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        addToAuto(json.getString("id"), json.getString("nama"));
                    }
                    addToAutoList();
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
                JSONObject json = control.getJSONfromURL("http://rofiimron.web44.net/android/allstartlocation.php");
                jarray = json.getJSONArray("faskes");
                Log.e("iki ", json.toString());
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


    private class SearchstartLocation extends AsyncTask<Void, Void, JSONArray> {

        ProgressDialog dialog = new ProgressDialog(SearchCustom.this);
        private String id;

        public void setId(String id) {
            this.id = id;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("id", id));

            try {
                JSONObject json = control.executeHttpPost("http://rofiimron.web44.net/android/searchstartlocation.php", post);
                jarray = json.getJSONArray("faskes");
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
                        //Log.e("Error ", json.toString());
                        lat = Double.parseDouble(json.getString("latitude"));
                        lng = Double.parseDouble(json.getString("longitude"));
                        createstartMarker(lat, lng, json.getString("nama"));
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

    private void addToAuto(String id, String name){
        AutoItems items = new AutoItems();
        items.setId(id);
        items.setName(name);
        Autolist.add(items);
    }

    private void addToAutoList(){

        autoAdapter = new AutoAdapter(this, Autolist);
        autoCompleteTextView.setAdapter(autoAdapter);
    }


    private class SeachNominal extends AsyncTask<Void, Void, JSONArray> {

        ProgressDialog dialog = new ProgressDialog(SearchCustom.this);
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
//            post.add(new BasicNameValuePair("kecamatan", name));

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


    private class SeachData extends AsyncTask<Void, Void, JSONArray>{

        ProgressDialog dialog = new ProgressDialog(SearchCustom.this);
        private String nama, nominal;

        public void setNama(String nama) {
            this.nama = nama;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jarray = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("kecamatan", nama));
            post.add(new BasicNameValuePair("jenis", nominal));

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
//            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//            symbols.setDecimalSeparator(',');
//            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);

            if(jsonArray != null){
                try {
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject json = jsonArray.getJSONObject(i);
                        lat = Double.parseDouble(json.getString("latitude"));
                        lng = Double.parseDouble(json.getString("longitude"));
                        createMarker(lat,lng, json.getString("nama"), json.getString("alamat"), json.getString("id"));
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


    private class GetBank extends AsyncTask<Void, Void, JSONArray>{

        ProgressDialog dialog = new ProgressDialog(SearchCustom.this);

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
            bank.add(value);
        }else if(type == 1){
            this.nominal.add(value);
        }
    }

    private void addToSpinner(int type){
        if(type==0){
            ArrayAdapter<String> spinnBank = new ArrayAdapter<String>(this,R.layout.search_content, bank);
            spinnerbank.setAdapter(spinnBank);
        }else if(type==1){
            ArrayAdapter<String> spinnNominal = new ArrayAdapter<String>(this, R.layout.search_content, nominal);
            spinnernominal.setAdapter(spinnNominal);
        }
        bank = new ArrayList<String>();
        nominal = new ArrayList<String>();
        nominal.add("Semua Fasilitas Kesehatan");
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
                    .zoom(13)
                    .bearing(360)
                    .tilt(15)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }catch (Exception e){
            Log.e("Error " , e.getMessage());
        }
    }

    public void createstartMarker(double latitude, double longitude, String title){
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title(title));
        markers.put(String.valueOf(latitude)+ "_" + String.valueOf(longitude), new Node1("0", title, "start"));
        startlat = String.valueOf(latitude);
        startlng = String.valueOf(longitude);
        startname = title;
    }

    public void createMarker(double latitude, double longitude, String title, String keterangan, String id){
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        markers.put(String.valueOf(latitude)+ "_" + String.valueOf(longitude), new Node1(id, title, keterangan));
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
                if(getInfoMarker(marker, "id").equals("0")){

                }else {
                    sn = Snackbar
                            .make(view, getInfoMarker(marker, "nama") + "\n" + getInfoMarker(marker, "alamat"), Snackbar.LENGTH_INDEFINITE)
                            .setAction("Rute", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(id_auto==null){
                                        Toast.makeText(SearchCustom.this, "Start lokasi anda salah", Toast.LENGTH_SHORT).show();
                                    }else{
                                        //Toast.makeText(MainActivity.this, getId(marker), Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), CustomRute.class);
                                        Bundle b = new Bundle();
                                        b.putString("id", getInfoMarker(marker, "id"));
                                        b.putString("nama", getInfoMarker(marker, "nama"));
                                        b.putString("alamat", getInfoMarker(marker, "alamat"));
                                        b.putString("lat", getInfoMarker(marker, "lat"));
                                        b.putString("lng", getInfoMarker(marker, "lng"));
                                        b.putString("startlat",startlat);
                                        b.putString("startlng",startlng);
                                        b.putString("startname", startname);
                                        i.putExtra("parse", b);
                                        startActivity(i);
                                    }
                                }
                            });
                    sn.setActionTextColor(Color.BLACK);
                    View snackbarView = sn.getView();
                    snackbarView.setBackgroundColor(Color.WHITE);
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    sn.show();
                }
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

