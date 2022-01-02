package bgv.fit.bstu.eday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bgv.fit.bstu.eday.Models.User;

public class MainActivity extends AppCompatActivity {
    DBHelper databaseHelper;
    SQLiteDatabase db;
    WorkWithDB workWithDB;
    TextView weatherinfo;
    String apikey = "8ce6eba1a0fd32d37bc317025e1278de";
    String city = "Minsk";
   String readyurl = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"&units=metric&lang=ru";
    public static Integer UserId = 0;
    public static String UserName = "";
    public static String UserSurname = "";
    public static byte [] UserPhoto;
    public static String UserLogin = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DBHelper(getApplicationContext()); //???
        db = databaseHelper.getWritableDatabase();
        workWithDB = new WorkWithDB(db);
        weatherinfo = findViewById(R.id.weathertv);
        try {
            new GetURLData().execute(readyurl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Tasks(View view){
        Intent intent = new Intent(this,TaskActivity.class);
        startActivity(intent);
    }

    public void Profile(View view){
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    private class GetURLData extends AsyncTask<String, String, String>{

        protected void onPreExecute(){
            super.onPreExecute();
            weatherinfo.setText("Загрузка...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection(); //получаем объект
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line=reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();

                try {
                    if (reader != null) {
                        reader.close();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                weatherinfo.setText("В "+jsonObject.getString("name")+"е "+jsonObject.getJSONObject("main").getDouble("temp")+" и " + jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
            } catch(Exception e) {

            }
        }
    }
}