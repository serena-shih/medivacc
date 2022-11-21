package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity {

    // private instance variables
    private static ArrayList<String> storedNotes = new ArrayList<>();
    private static ArrayList<String> notesDates = new ArrayList<>();
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        //Code used to set up the web view and its settings
        web = findViewById(R.id.webView_articles);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new Callback());
        web.loadUrl("https://www.google.com/search?q=covid+article+" + GraphActivity.getDateStringView());
        TextView textView = findViewById(R.id.textView_date_articles);
        // Log.d("eee", GraphActivity.getDateStringView()); // debugging
        String displayedText = GraphActivity.getDateStringView();
        textView.setText(displayedText);
    }

    /**
     * Navigates to map
     * @param view
     */
    public void sendToMapActivity(View view) {
        Intent iMap = new Intent(ArticlesActivity.this, MapActivity.class);
        startActivity(iMap);
    }

    /**
     * Navigates to graph
     * @param view
     */
    public void sendToGraphActivity(View view) {
        Intent iGraph = new Intent(ArticlesActivity.this, GraphActivity.class);
        startActivity(iGraph);
    }

    /**
     * Navigates to timeline
     * @param view
     */
    public void sendToTimelineActivity(View view) {
        Intent iTimeline = new Intent(ArticlesActivity.this, TimelineActivity.class);
        startActivity(iTimeline);
    }

    /**
     * Saves inputted text to timeline
     * @param view
     */
    public void saveNoteToTimelineActivity(View view) {
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout_note_articles);
        String stringNote = textInputLayout.getEditText().getText().toString();
        TextView textView = findViewById(R.id.textView_date_articles);
        String dateString = (String) textView.getText();
        // Log.d("wtf", stringNote); // debugging
        storedNotes.add(stringNote);
        notesDates.add(dateString);
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }

    // instance variable getters
    public static ArrayList<String> getStoredNotes() {
        return storedNotes;
    }

    public static ArrayList<String> getNotesDates() {
        return notesDates;
    }
}