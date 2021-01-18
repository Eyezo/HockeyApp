package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ArticleActivity extends AppCompatActivity {

    ListView lstArticles;
    ArticleAdapter articleAdapter;
    List<Article> articleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_list);

        lstArticles = findViewById(R.id.lstArticles);
        articleArray = new ArrayList<>();
        lstArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ArticleActivity.this, FullArticle.class);
                intent.putExtra("url", articleArray.get(position).getLink());
                startActivity(intent);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("NEWS");
                setSupportActionBar(toolbar);
            }
        });
        new DownloadNews().execute();

    }
    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();

        }catch (IOException e)
        {
            return null;
        }
    }
    private class DownloadNews extends AsyncTask<Void,Void,Void> {
        Dialog dlg;
        @Override
        protected void onPreExecute() {
            dlg = new SpotsDialog.Builder().setContext(ArticleActivity.this).setTheme(R.style.Custom).build();
            dlg.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*
             *  uses an AsyncTask to make all the necessary connections and data retrieval in the background
             */
            try
            {
                URL url = new URL("http://feeds.news24.com/articles/sport/featured/topstories/rss");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url),"UTF_8");

                boolean insideItem = false;

                int eventType = xpp.getEventType();

                articleArray.clear();

                int count = 0;
                String title = "";
                String link = "";

                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if(xpp.getName().equalsIgnoreCase("title"))
                        {
                            if(insideItem)
                            {
                                count++;
                                title = xpp.nextText();
                            }
                        }
                        else if(xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem) {
                                count++;
                                link = xpp.nextText();
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    if(count == 2)
                    {
                        Article articles = new Article();
                        articles.setTitle(title);
                        articles.setLink(link);
                        articleArray.add(articles);
                        count = 0;
                    }

                    eventType = xpp.next();

                }

            }catch(MalformedURLException e)
            {
                Log.d("ArticleActivity", e.getMessage());
            }
            catch (IOException e)
            {
                Log.d("ArticleActivity", e.getMessage());
            }
            catch (XmlPullParserException e)
            {
                Log.d("ArticleActivity", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /*
             *  sets all the retrieved articles in an array
             */
            articleAdapter = new ArticleAdapter(ArticleActivity.this, articleArray);
            lstArticles.setAdapter(articleAdapter);
            dlg.dismiss();

        }


    }
}
