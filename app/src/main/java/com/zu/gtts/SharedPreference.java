package com.zu.gtts;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "GIFT_APP";
    public static final String FAVORITES  = "GIFTS";

    public SharedPreference() {
        super();
    }

    // THIS FOUR METHODS ARE USED FOR MAINTAINING FAVORITES.
    public void saveFavorite(Context context, List<Info> info) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(info);
        editor.putString(FAVORITES, jsonFavorites);
        editor.apply();
    }

    public void addFavorite(Context context, Info info) {
        List<Info> favorites = getFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList<>();
            favorites.add(info);
            saveFavorite(context, favorites);
        }
        else {
            favorites.add(info);
            saveFavorite(context,favorites);
        }
    }

    public void removeFavorite(Context context, int index) {
        ArrayList<Info> favorites = getFavorites(context);
        if (favorites != null) {
            //favorites = new ArrayList<>();
            favorites.remove(index);
            saveFavorite(context, favorites);
        }
    }

    public ArrayList<Info> getFavorites(Context context) {
        SharedPreferences settings;
        List<Info> favorites;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Info[] favoriteItems = gson.fromJson(jsonFavorites,
                    Info[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else {
            return new ArrayList<>();
        }
        return (ArrayList<Info>) favorites;
    }
}