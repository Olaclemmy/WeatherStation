package data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by speci on 3/26/2017.
 */

public class CityPreference {

    SharedPreferences preferences;

    public CityPreference(Activity activity){
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity(){
        return preferences.getString("city", "Lagos,NG");
    }

    public void setCity(String city){
        preferences.edit().putString("city", city).commit();
    }
}
