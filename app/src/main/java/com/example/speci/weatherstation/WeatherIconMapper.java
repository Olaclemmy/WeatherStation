package com.example.speci.weatherstation;

/**
 * Created by speci on 7/3/2017.
 */

public class WeatherIconMapper {

    public static int getWeatherResource(String id, int wId) {
        // Log.d("App", "Id ["+id+"]");
        if (wId == 500)
            return R.drawable.icon_13n;
        if (wId == 501)
            return R.drawable.icon_11n;
        if (wId == 212)
            return R.drawable.icon_13d;
        if (id.equals("01d"))
            return R.drawable.icon_01d;
        else if (id.equals("01n"))
            return R.drawable.icon_01n;
        else if (id.equals("02d") || id.equals("02n"))
            return R.drawable.icon_02d;
        else if (id.equals("03d") || id.equals("03n"))
            return R.drawable.icon_03d;
        else if (id.equals("03d") || id.equals("03n"))
            return R.drawable.icon_03d;
        else if (id.equals("04d") || id.equals("04n"))
            return R.drawable.icon_04d;
        else if (id.equals("09d") || id.equals("09n"))
            return R.drawable.icon_09d;
        else if (id.equals("10d") || id.equals("10n"))
            return R.drawable.icon_10d;
        else if (id.equals("11d") || id.equals("11n"))
            return R.drawable.icon_11d;
        else if (id.equals("13d") || id.equals("13n"))
            return R.drawable.icon_13d;
        else if (id.equals("50d") || id.equals("50n"))
            return R.drawable.icon_10n;
        return R.drawable.icon_01d;
    }
}
