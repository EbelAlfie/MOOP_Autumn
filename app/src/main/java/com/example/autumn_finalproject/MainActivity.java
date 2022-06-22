package com.example.autumn_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView weatherState, humidState, tempState, windState;
    ImageView weatherIcon;
    Button lokasi;
    private DBHandler dbHandler;

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

        WeatherModal temp = dbHandler.readWeathers(1);

        lokasi.setText(temp.getCity());
        weatherState.setText(temp.getWeather());
        tempState.setText(temp.getTemper());
        humidState.setText(temp.getHumid());
        windState.setText(temp.getWind());

        lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), chooseLocation.class);
                startActivity(i);
            }
        });
    }
}