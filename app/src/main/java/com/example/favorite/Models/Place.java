package com.example.favorite.Models;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private static final String PREF_NAME = "favorite";
    private static final String KEY_LIST = "places";

    public static boolean isNameExists(Context context, String name) {
        List<PlaceItem> list = getAll(context);
        for (PlaceItem item : list) {
            if (item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static List<PlaceItem> getAll(Context context) {
        List<PlaceItem> resultList = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonString = prefs.getString(KEY_LIST, null);

        if (jsonString == null) {
            return resultList;
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String name = obj.getString("name");
                String description = obj.getString("description");
                double latitude = obj.getDouble("latitude");
                double longitude = obj.getDouble("longitude");

                resultList.add(new PlaceItem(name, description, latitude, longitude));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public static void addPlace(Context context, PlaceItem item) {
        List<PlaceItem> list = getAll(context);
        list.add(item);
        saveList(context, list);
    }

    public static void updatePlace(Context context, PlaceItem oldPlace, PlaceItem newPlace) {
        List<PlaceItem> list = getAll(context);
        for (int i = 0; i < list.size(); i++) {
            PlaceItem p = list.get(i);
            if (p.getName().equals(oldPlace.getName()) && 
                p.getLatitude() == oldPlace.getLatitude() && 
                p.getLongitude() == oldPlace.getLongitude()) {
                list.set(i, newPlace);
                break;
            }
        }
        saveList(context, list);
    }

    public static void deletePlace(Context context, PlaceItem item) {
        List<PlaceItem> list = getAll(context);
        for (int i = 0; i < list.size(); i++) {
            PlaceItem p = list.get(i);
            if (p.getName().equals(item.getName()) && 
                p.getLatitude() == item.getLatitude() && 
                p.getLongitude() == item.getLongitude()) {
                list.remove(i);
                break;
            }
        }
        saveList(context, list);
    }

    private static void saveList(Context context, List<PlaceItem> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray jsonArray = new JSONArray();

        for (PlaceItem item : list) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("name", item.getName());
                obj.put("description", item.getDescription());
                obj.put("latitude", item.getLatitude());
                obj.put("longitude", item.getLongitude());

                jsonArray.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        editor.putString(KEY_LIST, jsonArray.toString());
        editor.apply();
    }
}
