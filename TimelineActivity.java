package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.graphics.Color.rgb;
import static android.view.Gravity.CENTER_HORIZONTAL;

public class TimelineActivity extends AppCompatActivity {

    // declaration of LinearLayout object used to add text views into the linear layout
    private LinearLayout timeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // takes an array of strings and makes each individual item into a text view on the linear layout
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<String> textArrayDates = new ArrayList<>();
        textArray = ArticlesActivity.getStoredNotes();
        textArrayDates = ArticlesActivity.getNotesDates();

        // wires timeline scroll view
        timeline = findViewById(R.id.scroll_area_timeline);

        // creates and formats a text view for each note saved by the user
        for( int i = 0; i < textArray.size(); i++ )
        {
            TextView textView = new TextView(this);
            TextView textViewDates = new TextView(this);
            textView.setText(textArray.get(i));
            textViewDates.setText(textArrayDates.get(i));
            textView.setWidth(400);
            textView.setHeight(200);
            textViewDates.setWidth(100);
            textViewDates.setHeight(50);
            textView.setGravity(CENTER_HORIZONTAL);
            textViewDates.setGravity(CENTER_HORIZONTAL);
            textView.setBackgroundColor(rgb(255, 255, 255));
            textViewDates.setBackgroundColor(rgb(255, 255, 255));
            timeline.addView(textView);
            timeline.addView(textViewDates);
            textView.setX(50f * i);
            textView.setY(100f);
            textViewDates.setX(50f * i - 250);
            textViewDates.setY(300f);
            textViewDates.setTextSize(5);
        }
    }

    /**
     * Navigates to article
     * @param view
     */
    public void sendToArticleActivity(View view) {
        Intent iArticles = new Intent(TimelineActivity.this, ArticlesActivity.class);
        startActivity(iArticles);
    }
}