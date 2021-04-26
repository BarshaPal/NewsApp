package com.example.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<News> extractNews(String Url) {
        Log.e("List Loder", "List Loder called");
        // Create an empty ArrayList that we can start adding earthquakes to
        URL url = createurl(Url);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<News> News = extractFeatureFromJson(jsonResponse);

        return News;
    }

    private static URL createurl(String stringurl) {
        URL url=null;
        try{
            url=new URL(stringurl);
        }catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Error with creating URL ", e);

        }
        return url;
    }


    private static List<News> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<News> newNews = new ArrayList<>();
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray reasult = response.getJSONArray("results");
            for (int i = 0; i < reasult.length(); i++) {
                JSONObject currentreasult = reasult.getJSONObject(i);
                String title = "";
                String section="";
                String author="";
                String date="";
                String url="";
                if (currentreasult.has("webTitle")) {
                    title = currentreasult.getString("webTitle");


                }
                if (currentreasult.has("sectionName")) {
                    section = currentreasult.getString("sectionName");


                }

                if (currentreasult.has("webPublicationDate")) {
                    date = currentreasult.getString("webPublicationDate");


                }
                if (currentreasult.has("webUrl")) {
                    url = currentreasult.getString("webUrl");


                }
                News current=new News(title,section,date,url);
                newNews.add(current);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            ;
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        // Return the list of earthquakes
        return newNews;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
