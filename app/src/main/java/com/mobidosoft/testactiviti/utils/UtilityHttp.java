package com.mobidosoft.testactiviti.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.Formatter;

/**
 * Created by RP on 3/17/2015.
 */

public class UtilityHttp {

    private static final String LOG_TAG =UtilityHttp.class.getSimpleName();

    public static String getJsonStringFromNetwork() {
        Log.d(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String team = "81";
        String timeFrame = "p30";

        try {
            final String FIXTURE_BASE_URL = "http://192.168.0.180:3000/hello.json";
            //final String FIXTURE_BASE_URL = "http://192.168.3.149:3000/hello.json";
            //final String FIXTURE_PATH = "fixtures";
            //final String TIME_FRAME_PARAMETER = "timeFrame";

            Uri builtUri = Uri.parse(FIXTURE_BASE_URL).buildUpon()
                    //.appendPath(team)
                    //.appendPath(FIXTURE_PATH)
                   // .appendQueryParameter(TIME_FRAME_PARAMETER, timeFrame)
                    .build();
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            //urlConnection.setRequestProperty("X-Auth-Token","889d3c9a6227492b801b9685dc123f18");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            return buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public static String[] parseFixtureJson(String fixtureJson) throws JSONException {
        //JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<>();

        final String ID = "id";
        final String NAME = "name";
        final String CREATED_AT = "created_at";
        final String UPDATED_AT = "updated_at";
        //final String HOME_SCORE = "goalsHomeTeam";
        //final String AWAY_SCORE = "goalsAwayTeam";

        JSONArray fixturesArray = new JSONArray(fixtureJson);


        for (int i = 0; i < fixturesArray.length(); i++) {
            String id;
            String name;
            String created_at;
            String updated_at;
            JSONObject matchObject = fixturesArray.getJSONObject(i);
            //JSONObject resultObject = jsonObject.getJSONObject(i);

            id = matchObject.getString(ID);
            name = matchObject.getString(NAME);
            created_at = matchObject.getString(CREATED_AT);
            updated_at = matchObject.getString(UPDATED_AT);



            String resultString = new Formatter().format("%s: - %s", id, name).toString();
            result.add(resultString);
        }
        return result.toArray(new String[result.size()]);
    }




}
