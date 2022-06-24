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
    TextView txt_lat, txt_lon;
    private DBHandler dbHandler;
    GpsTracker gpsTracker;

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

        txt_lat = (TextView) findViewById(R.id.txt_lat);
        txt_lon = (TextView) findViewById(R.id.txt_lon);

        dbHandler = new DBHandler(chooseLocation.this);

        chooseLoc.setOnClickListener(new View.OnClickListener() { //Get Weather With City Name
            @Override
            public void onClick(View view) {
                String temp = String.valueOf(inputLocation.getText());

//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                dbHandler.updateWeather(1, "Jakarta", "Overcast", "20*C", "25%", "1m/s North");
//                startActivity(i);
            }
        });

        getLoc.setOnClickListener(new View.OnClickListener() { //Get Weather with current location
            @Override
            public void onClick(View view) {
                    txt_lat.setText(getLocs(1));
                    txt_lon.setText(getLocs(2));

//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                dbHandler.updateWeather(1, "Bandung", "Clear", "30*C", "50%", "1.5m/s South");
//                startActivity(i);
            }
        });
    }

    public String getLocs(int ID) { //ID  1 = lat, ID 2 = lon
        String t_lat = "0";
        String t_lon = "0";
        gpsTracker = new GpsTracker(chooseLocation.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            t_lat = String.valueOf(latitude);
            t_lon = String.valueOf(longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
        if(ID == 1){
        return t_lat;
        } else if(ID == 2) {
            return t_lon;
        }else{
            return "0";
        }
    }

}
