package com.example.autumn_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class AddAlert extends AppCompatActivity {
    EditText inputLocation, inputTime;
    Button chooseLoc, getLoc;
    private AlertDBHandler dbHandler;
    GpsTracker gpsTracker;

    private String cityTemp, weatherTemp, temperatureTemp, humidityTemp, windTemp; //DAV pasti ga akan ditampilin di text view sini kan? variable itu bisa buat assign ke database/ pass ke activity main, dll sesuai kebutuhan

    private final String apiKey = "APPID=d558cd5956417860a943c0df0a197172";
    private final String units = "&units=metric"; //Unit metric/imperial
    private final String url1 = "http://api.openweathermap.org/data/2.5/forecast?" + apiKey + units;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);
        inputLocation = findViewById(R.id.inputLocationAlert);
        inputTime = findViewById(R.id.inputTimeAlert);

        chooseLoc = findViewById(R.id.btn_chooseLocAlert);
        getLoc = findViewById(R.id.btn_currentLocAlert);
        dbHandler = new AlertDBHandler(AddAlert.this); //DB Access

        cityTemp = null; //DAV kalo" belum ada isinya
        weatherTemp = null;//DAV
        temperatureTemp = null;//DAV
        humidityTemp = null; //DAV
        windTemp = null;//DAV

        chooseLoc.setOnClickListener(new View.OnClickListener() { //Get Weather With City Name
            @Override
            public void onClick(View view) {
                String query = String.valueOf(inputLocation.getText());
                String cnt = "1";
                if(cnt != ""){
                    cnt = String.valueOf(inputTime.getText());;
                }else{
                    int cnt_n = Integer.valueOf(cnt) * 6;
                    cnt = String.valueOf(cnt_n);
                }
                String param = "&q=" + query + "&cnt=" + cnt; //DAV parameternya nama kota
                try {
                    getData(param); //DAV fetch JSON data based on url + param (paramnya aja soalnya cuman itu yang beda)
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(), MainActivity.class); //Go to MainActivity
                startActivity(i);
            }
        });

        getLoc.setOnClickListener(new View.OnClickListener() { //Get Weather with current location
            @Override
            public void onClick(View view) {
                String lat = getLocs(1); //DAV asign getLocs lat
                String lon = getLocs(2); //DAV asign getLocs lon
                String cnt = String.valueOf(inputTime.getText());
                String param = "&lon=" + lon + "&lat=" + lat + "&cnt=" + cnt; //DAV parameternya longitude dan latitude. jadi url+ param juga
                try {
                    getData(param);//DAV untuk fetch data based on lon and lat
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void getData(String param) throws UnsupportedEncodingException { //DAV ini fungsi utk get data
        AsyncHttpClient client = new AsyncHttpClient();
        String finalUrl = url1 + param; //DAV final url. Antara url1+ &q=namakota atau url1 + &lon=blabla&lat=blabla
        finalUrl = URLEncoder.encode(url1 + param, "UTF-8"); //DAV encode biar pencariannya lebih cepet
        client.get(finalUrl, new JsonHttpResponseHandler() { //DAV make a request
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) { //DAV kalau success, get datanya berdasarkan parameter response bentuk JSONObject
                try {
                    //DAV masukin seluruh data yang dibutuhkan ke variabel. Kalau2 mau di assign ke database, dll sesuai kebutuhan
                    cityTemp = response.getJSONObject("city").getString("name");
                    weatherTemp = response.getJSONArray("weather").getJSONObject(0).optString("description");
                    weatherTemp = toTitleCase(weatherTemp); //Title case Weather
                    temperatureTemp = response.getJSONObject("main").optString("temp") + "°C"; //current temp add °C
                    humidityTemp = response.getJSONObject("main").optString("humidity") + "%"; //Add %
                    int windDeg = Integer.valueOf(response.getJSONObject("wind").optString("deg"));
                    String winDir_s = wind_direction(windDeg);
                    windTemp = response.getJSONObject("wind").optString("speed") + " m/s " + winDir_s;
                    String time = "1"; //TODO: Nanti diganti

                    dbHandler.addNewAlert(time, cityTemp, weatherTemp, temperatureTemp, humidityTemp, windTemp); //Update DB after API request
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getLocs(int ID) { //ID  1 = lat, ID 2 = lon
        String t_lat = "0";
        String t_lon = "0";
        gpsTracker = new GpsTracker(AddAlert.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            t_lat = String.valueOf(latitude);
            t_lon = String.valueOf(longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
        if (ID == 1) {
            return t_lat;
        } else if (ID == 2) {
            return t_lon;
        } else {
            return "0";
        }
    }

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

    public String wind_direction(int degree) {
        String temp = "n/a";
        if (degree >= 336 || degree <= 25) {
            temp = "N";
        } else if (degree >= 26 && degree <= 65) {
            temp = "NE";
        } else if (degree >= 66 && degree <= 115) {
            temp = "E";
        } else if (degree >= 116 && degree <= 155) {
            temp = "SE";
        } else if (degree >= 156 && degree <= 205) {
            temp = "S";
        } else if (degree >= 206 && degree <= 245) {
            temp = "SW";
        } else if (degree >= 246 && degree <= 295) {
            temp = "W";
        } else if (degree >= 296 && degree <= 335) {
            temp = "NW";
        }
        return temp;
    }
}
