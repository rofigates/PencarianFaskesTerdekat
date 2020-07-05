package maps.pencarianfaskes.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import maps.pencarianfaskes.R;
import maps.pencarianfaskes.control.GetMylocation;
import maps.pencarianfaskes.control.InternetState;

/**
 * Created by RG 7 on 15/04/2017.
 */

public class Splash extends AppCompatActivity {

    //Set waktu lama splashscreen
    private static int splashInterval = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    new CekConnection().execute();
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }


    private class CekConnection extends AsyncTask<Void, Void, Boolean> {


        InternetState internet ;
        GetMylocation location ;
        Integer option = 0;

        @Override
        protected void onPreExecute() {

            internet = new InternetState(Splash.this);
            location = new GetMylocation(Splash.this);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }else {
                if(option == 1){
                    Toast.makeText(Splash.this, "Please turn on internet connection", Toast.LENGTH_SHORT).show();
                }else if(option == 2){
                    location.showSettingsAlert();
                }
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean result = false;
            try {
                if(location.isGpsOn() && internet.isConnect()){
                    result = true;
                }else{
                    if(!internet.isConnect()){
                        option = 1 ;
                    }else if(!location.isGpsOn()){
                        option = 2 ;
                    }
                    result = false;
                }
            }catch (Exception  e){
                Log.e("Error " , e.getMessage());
                result = false;
            }
            return result;
        }
    }

}
