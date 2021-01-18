package com.example.eyezo.hockeyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class FullArticle extends AppCompatActivity {

    WebView wbView;
    ProgressBar pbLin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_article);

        wbView = findViewById(R.id.webView);
        pbLin = findViewById(R.id.pbWeb);

        pbLin.setMax(100);

        String link = getIntent().getStringExtra("url");
        wbView.getSettings().setJavaScriptEnabled(true);
        wbView.setWebViewClient(new WebViewClient());
        wbView.loadUrl(link);
        wbView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbLin.setProgress(newProgress);
            }
        });

    }
}
