package com.example.autumn_finalproject;

public class CourseModal {
    private String Date;
    private String City;
    private String Weather;
    private String Temperature;
    private String Humidity;
    private String Wind;
    private int id;

    // creating getter and setter methods
    public String getDate() {
        return Date;
    }

    public void setDate(String x) {
        this.Date = x;
    }
    public String getCity() {
        return City;
    }

    public void setCity(String x) {
        this.City = x;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String x) {
        this.Weather = x;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String x) {
        this.Temperature = x;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String x) {
        this.Humidity = x;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String x) {
        this.Wind = x;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // constructor
    public CourseModal(String Date, String City, String Weather, String Temperature, String Humidity, String Wind) {
        this.Date = Date;
        this.City = City;
        this.Weather = Weather;
        this.Temperature = Temperature;
        this.Humidity = Humidity;
        this.Wind = Wind;
    }
}
