package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    private static final int BOOK_LOADER_ID = 1;
    private static String news_request_url="http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";
    public NewsAdapter mAdapter;
    public TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView newsListView = (ListView) findViewById(R.id.listview);
        mAdapter = new NewsAdapter(MainActivity.this, new ArrayList<News>());

        newsListView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);

         mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                News news_caurrent=mAdapter.getItem(position);
                Uri bookUri = Uri.parse(news_caurrent.getMlink());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                try {
                    startActivity(websiteIntent);

                } catch(Exception e) {
                    Toast.makeText(MainActivity.this, "NO Book Available", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, news_request_url);
          }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> Newslist) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        // Clear the adapter of previous book data
        mAdapter.clear();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            if (Newslist != null && !Newslist.isEmpty()) {
                mAdapter.addAll(Newslist);
            }
            else{
                mEmptyStateTextView.setText("No Books Found!");

            } }else {

            mEmptyStateTextView.setText("No Internet Connection!");

        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
