package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InfoActivity extends AppCompatActivity {

    // private instance variable
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // code used to set up the web view and its settings
        web = findViewById(R.id.webView_Info);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new InfoActivity.Callback());
        web.loadUrl("https://sites.google.com/stu.wvusd.org/covid-19-tracker/tips-and-tricks");
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }

    /**
     * Navigates to main
     * @param view
     */
    public void sendToMainActivity(View view) {
        Intent iMain = new Intent(InfoActivity.this, MainActivity.class);
        startActivity(iMain);
    }
}