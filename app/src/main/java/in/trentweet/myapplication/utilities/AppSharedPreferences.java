package in.trentweet.myapplication.utilities;

import android.content.Context;
import android.content.SharedPreferences;


public class AppSharedPreferences {

    private SharedPreferences preferences;


    private String USER_ID = "id";
    private String UNIQUE_TOKEN = "unique_token";
    private String USER_NAME = "user_name";

    public AppSharedPreferences(Context context) {
        String APP_PREF_NAME = "APP_PREF";
        int PRIVATE_MODE = 0;
        preferences = context.getSharedPreferences(APP_PREF_NAME, PRIVATE_MODE);
    }

    public void saveId(Integer id) {
        try {
            preferences.edit().putInt(USER_ID, id).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return preferences.getInt(USER_ID, 0);
    }

    public void saveToken(String token) {
        try {
            preferences.edit().putString(UNIQUE_TOKEN, token).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getToken() {
        return preferences.getString(UNIQUE_TOKEN, null);
    }

    public void saveUserName(String name) {
        try {
            preferences.edit().putString(USER_NAME, name).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getUserName() {
        return preferences.getString(USER_NAME, null);
    }


    public void clearAllSharedPreferences() {
        preferences.edit().clear().apply();
    }

}
