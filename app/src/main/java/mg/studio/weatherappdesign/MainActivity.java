package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] Week= {"MON","TUE","WED","THU","FRI","SAT","SUN"};
    String WeatherToday;
    String Weather1;
    String Weather2;
    String Weather3;
    String Weather4;

    public int getWeatherUpdate(String weatherd){
        switch (weatherd) {
            case "Clear":
                return R.drawable.sunny_small;
            case "Clouds":
                return R.drawable.partly_sunny_small;
            case "Rain":
                return R.drawable.rainy_small;
            case "Wind":
                return R.drawable.windy_small;
        }
        return R.drawable.rainy_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set date
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEEE");// Date
        //获取当前日期
        simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
        ((TextView) findViewById(R.id.tv_date)).setText(simpleDateFormat.format(today));
        simpleDateFormat=new SimpleDateFormat("EEEE");
        String weekday = simpleDateFormat.format(today);
        ((TextView) findViewById(R.id.tv_todayweekday)).setText(weekday);
        int iweek=0;
        switch (weekday){
            case "Monday":iweek = 0;
            case "Tuesday":iweek = 1;
            case "Wednesday":iweek = 2;
            case "Thursday":iweek = 3;
            case "Friday":iweek = 4;
            case "Saturday":iweek = 5;
            case "Sunday":iweek = 6;
        }
        ((TextView) findViewById(R.id.tv_next1)).setText(Week[(iweek++)%6]);
        ((TextView) findViewById(R.id.tv_next2)).setText(Week[(iweek++)%6]);
        ((TextView) findViewById(R.id.tv_next3)).setText(Week[(iweek++)%6]);
        ((TextView) findViewById(R.id.tv_next4)).setText(Week[(iweek++)%6]);
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            //String stringUrl = "https://mpianatra.com/Courses/info.txt";
            String stringUrl = "https://mpianatra.com/Courses/forecast.json";
            //String stringUrl = "http://api.openweathermap.org/data/2.5/forecast?q=chongqing&units=metric&appid=1d7518337b40bcc5557731e9ddaf67bb";
            //String stringUrl = null;
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    Log.d("TAG", line);
                    /*String jsonstring = buffer.toString();
                    Weather weather = getcontent(jsonstring);
                    LList weatherlist = weather.listList.get(1);
                    int tempf = weatherlist.main.temp;
                    int tempc = (int) ((tempf-32)/1.8);
                    //parseJSON(line);*/
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                String jsonString = buffer.toString();
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonObjectlist = jsonObject.getJSONArray("list");

                JSONObject jsonObjectDay = jsonObjectlist.getJSONObject(0);
                JSONObject jsonObjectMain = jsonObjectDay.getJSONObject("main");
                String temperature = jsonObjectMain.getString("temp");

                //Forcast the next four days
                JSONArray FourdayList;
                //weather today
                jsonObjectDay = jsonObjectlist.getJSONObject(0);
                FourdayList = jsonObjectDay.getJSONArray("weather");
                jsonObjectMain = FourdayList.getJSONObject(0);
                WeatherToday = jsonObjectMain.getString("main");
                Log.i("Todayweather", jsonObjectDay.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(getWeatherUpdate(WeatherToday)));//setImageResource(getWeatherSmallImg(weatherText));
                    }
                });

                //weather 1
                jsonObjectDay = jsonObjectlist.getJSONObject(8);
                FourdayList = jsonObjectDay.getJSONArray("weather");
                jsonObjectMain = FourdayList.getJSONObject(0);
                Weather1 = jsonObjectMain.getString("main");
                Log.i("weather1", jsonObjectDay.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) findViewById(R.id.img_next1)).setImageResource(getWeatherUpdate(Weather1));
                    }
                });

                //weather2
                jsonObjectDay = jsonObjectlist.getJSONObject(16);
                FourdayList = jsonObjectDay.getJSONArray("weather");
                jsonObjectMain = FourdayList.getJSONObject(0);
                Weather2 = jsonObjectMain.getString("main");
                Log.i("weather2", jsonObjectDay.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) findViewById(R.id.img_next2)).setImageResource(getWeatherUpdate(Weather2));
                    }
                });

                //weather3
                jsonObjectDay = jsonObjectlist.getJSONObject(24);
                FourdayList = jsonObjectDay.getJSONArray("weather");
                jsonObjectMain = FourdayList.getJSONObject(0);
                Weather3 = jsonObjectMain.getString("main");
                Log.i("weather3", jsonObjectDay.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) findViewById(R.id.img_next3)).setImageResource(getWeatherUpdate(Weather3));
                    }
                });

                //weather4
                jsonObjectDay = jsonObjectlist.getJSONObject(32);
                FourdayList = jsonObjectDay.getJSONArray("weather");
                jsonObjectMain = FourdayList.getJSONObject(0);
                Weather4 = jsonObjectMain.getString("main");
                Log.i("weather4", jsonObjectDay.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ImageView) findViewById(R.id.img_next4)).setImageResource(getWeatherUpdate(Weather4));
                    }
                });
                return temperature;


                //return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /*private Weather getcontent(String content) {
            //HashMap<String hashMap = new HashMap<>();
            String temp = new String();
            try {
                //JSONObject jsonObject = new JSONObject(content);
                //JSONArray jsonArray = jsonObject.getJSONArray("Heweather");
                JSONArray jsonArray = new JSONArray(content);
                String weatherContent = jsonArray.getJSONObject(0).toString();
                Log.i("MainActivity",weatherContent);
                return new Gson().fromJson(weatherContent, Weather.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
            }*/

       /* public void parseJSON(String jsonData){
            JSONArray jsonArray = new JSONArray(jsonData);
            Gson gson = new Gson();
            List<Weather> weatherList = gson.fromJson(jsonData,new TypeToken<List<Weather>>(){}.getType());
            for (Weather weather:weatherList){
                //Log.d("Temp",weather.getTemp());
                Log.d("ID",weather.getId());
                Log.d("Date",weather.getDate());
                Log.d("Time",weather.getTime());
                Log.d("Temp",weather.getTemp());
                Log.d("Main",weather.getMain());
            }
            //return weatherList;
        }*/


        @Override
        protected void onPostExecute(String temperature) {
            //Update the temperature displayed
            Toast.makeText(MainActivity.this, "Information updated.", Toast.LENGTH_SHORT).show();
            //int tempf = Integer.parseInt(temperature);
            float tempf = Float.parseFloat(temperature);
            int tempc = (int) (5*(tempf-32)/9);//change Fahrenheit to Celsius
            //float tempc = tempf/10;
            String stempc=String.valueOf(tempc);
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(stempc);

        }
    }
}
