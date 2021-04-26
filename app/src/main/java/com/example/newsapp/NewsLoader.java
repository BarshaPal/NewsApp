package com.example.newsapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;


    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e("Start Loder","Start Loder called");
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            Log.e("Background Loder","Null Background Loder called");
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> earthquakes = Utils.extractNews(mUrl);
        return earthquakes;
    }
}
