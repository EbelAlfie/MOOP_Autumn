package com.example.autumn_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "autumn_db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "Weather";

    private static final String ID_COL = "id";
    private static final String DATE_COL = "date";
    private static final String CITY_COL = "city";
    private static final String WEATHER_COL = "weather";
    private static final String TEMPERATURE_COL = "temperature";
    private static final String HUMIDITY_COL = "humidity";
    private static final String WIND_COL = "wind";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,%s TEXT,%s TEXT,%s TEXT, %s TEXT)", TABLE_NAME, ID_COL, DATE_COL, CITY_COL, WEATHER_COL, TEMPERATURE_COL, HUMIDITY_COL, WIND_COL);
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewWeather(String date, String city, String weather, String temperature, String humidity, String wind) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DATE_COL, date);
        values.put(CITY_COL, city);
        values.put(WEATHER_COL, weather);
        values.put(TEMPERATURE_COL, temperature);
        values.put(HUMIDITY_COL, humidity);
        values.put(WIND_COL, wind);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<CourseModal> readWeathers() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<CourseModal> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new CourseModal(cursorCourses.getString(1), cursorCourses.getString(4), cursorCourses.getString(2), cursorCourses.getString(3), cursorCourses.getString(4), cursorCourses.getString(5)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

    // we have created a new method for reading all the courses.
    public CourseModal readWeather(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM Weather WHERE DATE_COL =date", null);
        CourseModal Weather = new CourseModal(cursorCourses.getString(1), cursorCourses.getString(4), cursorCourses.getString(2), cursorCourses.getString(3), cursorCourses.getString(4), cursorCourses.getString(5));
        cursorCourses.close();
        return Weather;
    }

    // below is the method for updating our courses
    public void updateWeather(String date, String city, String weather, String temperature, String humidity, String wind) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATE_COL, date);
        values.put(CITY_COL, city);
        values.put(WEATHER_COL, weather);
        values.put(TEMPERATURE_COL, temperature);
        values.put(HUMIDITY_COL, humidity);
        values.put(WIND_COL, wind);

        db.update(TABLE_NAME, values, "name=?", new String[]{date});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
