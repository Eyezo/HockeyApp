package com.example.eyezo.hockeyapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private List<Article> articles;
    private Context context;


    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, R.layout.article, articles);

        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.article, parent, false);
        TextView title = v.findViewById(R.id.txtTitle);
        title.setText(articles.get(position).getTitle());
        return v;
    }
}
