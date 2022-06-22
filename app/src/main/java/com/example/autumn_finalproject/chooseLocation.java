package com.example.autumn_finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class chooseLocation extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private DBHandler dbHandler;

    public static String toTitleCase(String input) {//Capilalize Weather Name
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    EditText inputLocation;
    Button chooseLoc;
    Button currentLoc;
    private HandleXML obj;

    private String url1 = "http://api.openweathermap.org/data/2.5/weather?mode=xml&APPID=af9001dce8720bbe36288523ce329664";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        inputLocation = (EditText) findViewById(R.id.inputLocation);
        chooseLoc = (Button) findViewById(R.id.btn_chooseLoc);
        currentLoc = (Button) findViewById(R.id.btn_currentLoc);

        dbHandler = new DBHandler(chooseLocation.this);

//        String City = "Bandung";
//        String Weather = "Clear";
//        String Temperature = "30*C";
//        String Humidity = "50%";
//        String Wind = "1.5 m/s";
//        String WindDir = "North";


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String[] permissionStrings = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissionsIfNecessary(permissionStrings);
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);




        chooseLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lokasi;
                if(inputLocation.getText()==null){
                    lokasi = "Bandung";
                }else{
                    lokasi = String.valueOf(inputLocation.getText());
                }
                String finalUrl = url1+ "&q=" + lokasi;
                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) {
                    String City = obj.getCity();
                    String Weather = toTitleCase(obj.getWeather());
                    String Temperature = obj.getTemperature();
                    String Humidity = obj.getHumidity();
                    String Wind = obj.getWind()+" "+obj.getWindDir();
                    System.out.println("Choosen Loc");
                    System.out.println(City);
                    System.out.println(Weather);
                    System.out.println(Temperature);
                    System.out.println(Humidity);
                    System.out.println(Wind);
                    dbHandler.addNewWeather("now", City, Weather, Temperature, Humidity, Wind);
                }
            }
        });

        currentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                String finalUrl = url1+"&lon="+longitude+"&lat="+latitude;
                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) {
                    String City = obj.getCity();
                    String Weather = toTitleCase(obj.getWeather());
                    String Temperature = obj.getTemperature();
                    String Humidity = obj.getHumidity();
                    String Wind = obj.getWind()+" "+obj.getWindDir();
                    System.out.println("Current Loc");
                    System.out.println(City);
                    System.out.println(Weather);
                    System.out.println(Temperature);
                    System.out.println(Humidity);
                    System.out.println(Wind);
                    dbHandler.addNewWeather("now", City, Weather, Temperature, Humidity, Wind);
                }



                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for(String permission : permissions) {
            if(ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if(permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}

