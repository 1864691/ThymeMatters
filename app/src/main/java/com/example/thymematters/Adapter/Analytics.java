package com.example.thymematters.Adapter;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thymematters.R;

public class Analytics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        WebView view = findViewById(R.id.webviewid);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl("https://analytics.google.com/analytics/web/?authuser=0#/p286543659/reports/dashboard?params=_u..nav%3Dmaui%26_u..pageSize%3D25&r=firebase-overview&ruid=firebase-overview,app,firebase&collectionId=app");
    }
}