package com.example.thymematters;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class Analytics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        WebView webView = findViewById(R.id.webviewid);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://analytics.google.com/analytics/web/?authuser=0#/p286543659/reports/dashboard?params=_u..nav%3Dmaui%26_u..pageSize%3D25&r=firebase-overview&ruid=firebase-overview,app,firebase&collectionId=app");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");
    }
}