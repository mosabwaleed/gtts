package com.zu.gtts;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference_doctor {
    public static final String PREFS_NAME = "GIFT_APP";
    public static final String FAVORITES  = "GIFTS";

    public SharedPreference_doctor() {
        super();
    }

    // THIS FOUR METHODS ARE USED FOR MAINTAINING FAVORITES.
    public void saveFavorite(Context context, List<doctor_info> doctor_info) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(doctor_info);
        editor.putString(FAVORITES, jsonFavorites);
        editor.apply();
    }

    public void addFavorite(Context context, doctor_info doctor_info) {
        List<doctor_info> favorites = getFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList<>();
            favorites.add(doctor_info);
            saveFavorite(context, favorites);
        }
        else {
            favorites.add(doctor_info);
            saveFavorite(context,favorites);
        }
    }

    public void removeFavorite(Context context, int index) {
        ArrayList<doctor_info> favorites = getFavorites(context);
        if (favorites != null) {
            //favorites = new ArrayList<>();
            favorites.remove(index);
            saveFavorite(context, favorites);
        }
    }

    public ArrayList<doctor_info> getFavorites(Context context) {
        SharedPreferences settings;
        List<doctor_info> favorites;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            doctor_info[] favoriteItems = gson.fromJson(jsonFavorites,
                    doctor_info[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else {
            return new ArrayList<>();
        }
        return (ArrayList<doctor_info>) favorites;
    }
}