package com.example.klarify;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveLogin {
    // Saving the phone number when user logs in
    static final String PREF_USER_PHONE= "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPhone(Context ctx, String userPhone)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PHONE, userPhone);
        editor.apply();
    }

    public static String getPhone(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_PHONE, "");
    }

    public static void clearPhone(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
}
