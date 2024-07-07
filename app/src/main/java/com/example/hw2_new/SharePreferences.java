package com.example.hw2_new;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hw2_new.Logic.TopTenRecords;
import com.google.gson.Gson;

public class SharePreferences {

    private String DB_FILE = "DB_FILE";
    private static volatile SharePreferences instance = null;
    private SharedPreferences sharedPreferences ;

    private SharePreferences(Context context){
        sharedPreferences = context.getSharedPreferences(DB_FILE , Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if (instance ==null){
            synchronized (SharePreferences.class){ // only one can change
                if (instance == null)
                    instance = new SharePreferences(context);
            }
        }
    }

    public static SharePreferences getInstance(){
        return instance;
    }

    public String getString(String key , String defValue){
        return sharedPreferences.getString(key , defValue);
    }

    public void putString(String key , String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key , value);
        editor.apply();
    }

    public int getInt(String key , int defValue){
        return sharedPreferences.getInt(key , defValue);
    }

    public void putInt(String key , int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key , value);
        editor.apply();
    }

    public TopTenRecords loadFromJson(){
        String fromSP = getString("GameRecords" , "");
        Log.d("",fromSP);
        TopTenRecords tenRecordsFromJson = new Gson().fromJson(fromSP,TopTenRecords.class);
        Log.d("","" +tenRecordsFromJson );
        if (tenRecordsFromJson == null)
            tenRecordsFromJson = new TopTenRecords();

        return tenRecordsFromJson;
    }

    public void saveToJson(int score , double latitude, double longitude ){
        TopTenRecords topTenRecords;
        topTenRecords = loadFromJson();
        Log.d("records in save", "" + topTenRecords);
        topTenRecords.addItem(score + "",latitude,longitude);
        String jsonStr = new Gson().toJson(topTenRecords);
        putString("GameRecords" , jsonStr);
        Log.d("jsonStr", jsonStr.toString());
    }
}
