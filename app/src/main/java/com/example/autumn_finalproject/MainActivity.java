package com.example.autumn_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView  weatherState, humidState, tempState, windState;
    ImageView weatherIcon;
    Button lokasi;
    private DBHandler dbHandler;
    private ArrayList<CourseModal> courseModalArrayList;


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

        dbHandler = new DBHandler(MainActivity.this);
        CourseModal modal = courseModalArrayList.get(1);
        weatherState.setText(modal.getWeather());
        humidState.setText(modal.getHumidity());
        tempState.setText(modal.getTemperature());
        windState.setText(modal.getWind());
        lokasi.setText(modal.getTemperature());






        lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), chooseLocation.class);
                lokasi.setText("Bandung");
                startActivity(i);
            }
        });


    }
}