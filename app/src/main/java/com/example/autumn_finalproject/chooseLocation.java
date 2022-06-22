package com.example.autumn_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class chooseLocation extends AppCompatActivity {

    EditText inputLocation;
    Button chooseLoc, getLoc;
    TextView city, weather, temper, humid, wind;
    private DBHandler dbHandler;

    private String url1 = "http://api.openweathermap.org/data/2.5/weather?mode=xml&APPID=af9001dce8720bbe36288523ce329664";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        inputLocation = (EditText) findViewById(R.id.inputLocation);

        chooseLoc = (Button) findViewById(R.id.btn_chooseLoc);
        getLoc = (Button) findViewById(R.id.btn_currentLoc);

        city = (TextView) findViewById(R.id.txt_city);
        weather = (TextView) findViewById(R.id.txt_weather);
        temper = (TextView) findViewById(R.id.txt_temp);
        humid = (TextView) findViewById(R.id.txt_humid);
        wind = (TextView) findViewById(R.id.txt_wind);

        dbHandler = new DBHandler(chooseLocation.this);

        chooseLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = inputLocation.getTransitionName();

//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                dbHandler.updateWeather(1, "Jakarta", "Overcast", "20*C", "25%", "1m/s North");
//                startActivity(i);
            }
        });

        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                dbHandler.updateWeather(1, "Bandung", "Clear", "30*C", "50%", "1.5m/s South");
//                startActivity(i);
            }
        });
    }
}
