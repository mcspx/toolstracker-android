package com.mstack.toolstracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pitipong on 19/10/2558.
 */
public class PreferenceManager {

    public static final String MyPREFERENCES = "toolstracker";
    private SharedPreferences sharedpreferences;
    private final String baseAPI = "baseAPI";

    public PreferenceManager(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void SetBaseApi(String baseApi){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(baseAPI, baseApi);
        editor.commit();
    }

    public String getBaseApi(){
        if (sharedpreferences.contains(baseAPI)) {
            return sharedpreferences.getString(baseAPI, null);
        }
        return null;
    }

}
