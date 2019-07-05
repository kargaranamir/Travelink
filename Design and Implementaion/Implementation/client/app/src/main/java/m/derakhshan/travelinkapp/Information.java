package m.derakhshan.travelinkapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Information extends Signup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences Information = PreferenceManager.getDefaultSharedPreferences(this);

        String token = Information.getString("token", "null");
        boolean IsLoggedIn = Information.getBoolean("IsLoggedIn", false);

    }

}
