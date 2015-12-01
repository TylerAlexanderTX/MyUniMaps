package com.myunimaps.myunimaps;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class AboutUs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        WebView  webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.loadUrl("http://www.myunimap.com");

    }

    }

