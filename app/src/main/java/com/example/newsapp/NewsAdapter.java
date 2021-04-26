package com.example.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Activity context, ArrayList<News> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
            News currentword = getItem(position);
            TextView title=(TextView)listItemView.findViewById(R.id.idTitle);
            title.setText(currentword.getMtitle());
            TextView subtitle=(TextView)listItemView.findViewById(R.id.idTVsection);
            subtitle.setText(currentword.getMsubtitle());
            TextView date=(TextView)listItemView.findViewById(R.id.idTVPublishDate);
            date.setText(currentword.getMdate());


        }
        return listItemView;
    }
}
