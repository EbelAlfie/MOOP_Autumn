package com.example.autumn_finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AlertAdapter.OnAlertClickListener {

    TextView weatherState, humidState, tempState, windState;
    ImageView weatherIcon;
    Button lokasi;
    private DBHandler dbHandler;
    private RecyclerView weatherRecyclerView; //Dav
    private ArrayList<WeatherModal> weatherModalArrayList; //Dav
    private AlertAdapter alertAdapter;//Dav
    private WeatherModal temp; //Global variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherState = (TextView) findViewById(R.id.text_weather);
        humidState = (TextView) findViewById(R.id.text_humidity);
        tempState = (TextView) findViewById(R.id.text_temperature);
        weatherIcon = (ImageView) findViewById(R.id.img_cuaca);
        windState = (TextView) findViewById(R.id.text_wind);
        lokasi = (Button) findViewById(R.id.btn_location);
        weatherRecyclerView = (RecyclerView) findViewById(R.id.weatherRecyclerView);  //Dav
        weatherRecyclerView.setHasFixedSize(true);//Dav
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//Dav

        weatherModalArrayList = new ArrayList<>();//Dav
        dbHandler = new DBHandler(MainActivity.this);
        temp = dbHandler.readWeathers(1); //Declare Weather modal

        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            dbHandler.addNewWeather(1, "Bandung", "Clear", "30*C", "50%", "1.5m/s South");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            dbHandler.addNewWeather(2, "Jakarta", "Overcast", "20*C", "25%", "1m/s North");
        } catch (Exception e) {
            e.printStackTrace();
        }

        lokasi.setText(temp.getCity());
        weatherState.setText(temp.getWeather());
        tempState.setText(temp.getTemper());
        humidState.setText(temp.getHumid());
        windState.setText(temp.getWind());
        if(temp.getWeather().contains("Clear")){
            System.out.println("Clear");
        }else if(temp.getWeather().contains("Clouds")){
            System.out.println("Overcast");
        }else if(temp.getWeather().contains("Rain")){
            System.out.println("Rain");
        }else if(temp.getWeather().contains("Thunderstorm")){
            System.out.println("Storm");
        }else{
            System.out.println("Overcast");
        }
        /*TODO
        [x]Function Change Icon, if Contains::
            - Clear //clear
            - Overcast / Clouds //clouds
            - Rain //rain
            - Storm // thunderstorm
        Change sout to change img
         */

        weatherModalArrayList.add(temp); //DAV ubah aja sesukanya
        weatherModalArrayList.add(dbHandler.readWeathers(2));//DAV ini ubah aja sesuka hati

        alertAdapter = new AlertAdapter(weatherModalArrayList, getApplicationContext(), this); //Dav masukin arr list dan context main ke adapter

        weatherRecyclerView.setAdapter(alertAdapter);

        alertAdapter.setOnAlertClickListener(this); //DAV set onclicklistener palsu

        lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), chooseLocation.class);
                startActivity(i);
                /*TODO
                Fix DB bug not updateing. Harus 2x setting lokasi baru update
                 */
            }
        });
    }

    @Override
    public void onDeleteClick(int position) { //DAV
        weatherModalArrayList.remove(position) ;
        alertAdapter.notifyItemRemoved(position);
    }//DAV
}