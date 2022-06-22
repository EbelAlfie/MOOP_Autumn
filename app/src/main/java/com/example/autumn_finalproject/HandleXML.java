package com.example.autumn_finalproject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleXML {
    private String city = "city";
    private String weather = "weather";
    private String temperature = "temperature";
    private String humidity = "humidity";
    private String wind = "wind";
    private String windDir = "direction";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXML(String url) { //constructor
        this.urlString = url;
    }

    public String getCity() {
        return city;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind(){
        return wind;
    }

    public String getWindDir() {
        return windDir;
    }

    public void parseXMLandStore(XmlPullParser myParser) { //parses XML
        int event;
        String text = null;

        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case "city":
                                city = myParser.getAttributeValue(null, "value");
                            case "humidity":
                                humidity = myParser.getAttributeValue(null, "value")+"%";
                                break;
                            case "temperature":
                                double temp = Double.parseDouble(myParser.getAttributeValue(null, "value"))-273.15;
                                temperature = Double.toString(temp);
                                break;
                            case "speed":
                                wind = myParser.getAttributeValue(null, "value")+" Km/h";
                            case "direction":
                                windDir = myParser.getAttributeValue(null, "name");
                            case "weather" :
                                weather = myParser.getAttributeValue(null, "value");
                            default:
                                break;
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream stream = connect.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_REPORT_NAMESPACE_ATTRIBUTES, false);
                    myparser.setInput(stream, null);
                    parseXMLandStore(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
