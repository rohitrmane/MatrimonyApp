package com.example.rohit.testapplication.Service;

import android.util.Log;

import com.example.rohit.testapplication.Activity.HomeActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Service {

    public static final String URL = "https://randomuser.me/api/?results=10";

    public static String getProfileData(String api_url) {
        try{
            URL url = new URL(api_url);
            URLConnection connection = url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            return streamToString(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            reader.close();
        }
        catch (IOException e) {
            Log.d(HomeActivity.class.getSimpleName(), e.toString());
        }
        return stringBuilder.toString();
    }

}
