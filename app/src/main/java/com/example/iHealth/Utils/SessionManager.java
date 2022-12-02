package com.example.iHealth.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.iHealth.R;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public void saveLogin(Context context, String token) {
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("login", token);
        editor.apply();
    }

    public String readLogin(Context context){
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString("login", "false");
    }

    public void saveUserName(Context context, String token) {
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("user_name", token);
        editor.apply();

    }

    public String fetchUserName(Context context){
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name", null);
    }

    public void savePhoneNumber(Context context, String token) {
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("phone_number", token);
        editor.apply();

    }

    public String fetchPhoneNumber(Context context){
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString("phone_number", null);
    }

    public void saveResult(Context context, String token) {
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("result", token);
        editor.apply();

    }

    public String fetchResult(Context context){
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString("result", null);
    }


}
