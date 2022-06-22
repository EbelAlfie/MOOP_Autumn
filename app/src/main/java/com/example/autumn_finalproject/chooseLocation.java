package com.example.autumn_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class chooseLocation extends AppCompatActivity {

    EditText inputLocation;
    Button chooseLoc, getLoc;
    TextView city, weather, temper, humid, wind;


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

        chooseLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });





    }


}
