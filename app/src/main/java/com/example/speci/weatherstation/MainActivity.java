package com.example.speci.weatherstation;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import data.CityPreference;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView update;
    private TextView temperature;

    Weather weather = new Weather();

    CityPreference cityPreference;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Refreshing weather data", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    if (!isConnected(MainActivity.this)){
                        buildDialog(MainActivity.this).show();
                    }else {
                        renderWeatherData(cityPreference.getCity());
                    }
                }
            });

            cityName = (TextView) findViewById(R.id.city_text);
            temp = (TextView) findViewById(R.id.temp_text);
            iconView = (ImageView) findViewById(R.id.thumbnail_icon);
            description = (TextView) findViewById(R.id.cloud_text);
            humidity = (TextView) findViewById(R.id.humidity_text);
            pressure = (TextView) findViewById(R.id.pressure_text);
            wind = (TextView) findViewById(R.id.wind_text);
            sunrise = (TextView) findViewById(R.id.rise_text);
            sunset = (TextView) findViewById(R.id.set_text);
            update = (TextView) findViewById(R.id.update_text);
            temperature = (TextView) findViewById(R.id.temp_texts);


        if (!isConnected(MainActivity.this)){
            buildDialog(MainActivity.this).show();
        }else {

            cityPreference = new CityPreference(MainActivity.this);

            renderWeatherData(cityPreference.getCity());
        }
    }

    public void renderWeatherData(String city) {

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric"});
    }

    private class WeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {

            String data;
            try {
                data = ((new WeatherHttpClient()).getWeatherData(params[0]));
                weather = JSONWeatherParser.getWeather(data);
                Log.v("Data: ", weather.place.getCity()+ ","+ weather.place.getCountry());

                // Let's retrieve the icon
                weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

                return weather;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            int weatherIconImageResource = getResources().getIdentifier("icon_" + weather.currentCondition.getIcon(),
                    "drawable", getPackageName());

            DateFormat df = DateFormat.getTimeInstance();
            String sunriseDate = df.format(new Date(weather.place.getSunRise()));
            String sunsetDate = df.format((new Date(weather.place.getSunSet())));
            String updateDate = df.format(new Date((weather.place.getLastUpdate())));

            DecimalFormat decimalFormat = new DecimalFormat("##");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());
            String tempMaximum = decimalFormat.format(weather.currentCondition.getMaxTemp());
            String tempMinimum = decimalFormat.format(weather.currentCondition.getMinTemp());

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + tempFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity()+ "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure()+ "hpa");
            wind.setText("Wind: " + weather.wind.getSpeed()+ "mps");
            sunrise.setText("Sunrise: " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            update.setText("Last Update: " + updateDate);
            description.setText("Condition: " + weather.currentCondition.getCondition()+ "(" +
            weather.currentCondition.getDescription()+ ")");
            temperature.setText("Temperature: " + tempMaximum + "°C" + "/" + tempMinimum + "°C");
            //iconView.setImageResource(WeatherIconMapper.getWeatherResource(weather.currentCondition.getIcon(), weather.currentCondition.getWeatherId()));
            iconView.setImageResource(weatherIconImageResource);
        }
    }

    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("change city");

        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Lagos,NG");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               CityPreference cityPreference = new CityPreference(MainActivity.this);
               cityPreference.setCity(cityInput.getText().toString());

                String newCity = cityPreference.getCity();

                if (!isConnected(MainActivity.this)){
                    buildDialog(MainActivity.this).show();
                }else {
                    renderWeatherData(newCity);
                }

            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_cityId) {
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isConnected(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting())||(wifi != null && wifi.isConnectedOrConnecting())){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    public AlertDialog.Builder buildDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have mobile data or wifi to access weather data. Press OK to EXIT");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int which){
                finish();
            }
        });
         return builder;
    }
}
