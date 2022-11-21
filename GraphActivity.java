package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


public class GraphActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // private instance variables
    private static String dateStringView;
    private Spinner spinnerDataType;
    private Spinner spinnerDate;
    private Spinner spinnerRegion;
    private static ArrayList<Float> dayValue = new ArrayList<>();
    private static ArrayList<String> filteredDates = new ArrayList<>();
    private BarChart barChart; // declaration of the bar chart where data will be input in order to be displayed by the graph

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // connects bar chart object to the element on the screen
        barChart = (BarChart) findViewById(R.id.barGraph_graph);
        barChart.setOnChartValueSelectedListener(chartListener);
        barChart.setScaleYEnabled(false);

        // populates list with all countries
        int rowNum = MainActivity.getVaccinationSample().size();
        ArrayList<String> allCountries = new ArrayList<>();
        for (int i = 0; i < rowNum - 1; i++) {
            VaccinationSample sample = MainActivity.getVaccinationSample().get(i);
            String sampleCountry = sample.getCountry();
            boolean isFound = false;
            for (int j = 0; j < allCountries.size(); j++) {
                if (allCountries.get(j).equals(sampleCountry)) {
                    isFound = true;
                }
            }
            if (!isFound) {
                allCountries.add(sampleCountry);
            }
        }

        // Log.d("All Countries", allCountries.toString()); // debugging
        /* float graphXValue = 0;
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for( int i = 0; i < rowNum - 1; i++ ) {
            VaccinationSample sample = MainActivity.getVaccinationSample().get(i);
            String sampleCountry = sample.getCountry();
            String dateString = sample.getDate();
            if(sampleCountry.equals("United States")){
                filteredIndexes.add(i);
                filteredDates.add(dateString);
                graphXValue++;
                double VacValue = sample.getDailyVac();
                float VacFloat = (float)VacValue;
                barEntries.add(new BarEntry (graphXValue, VacFloat));
                Log.i(sampleCountry, "Wow");
            }
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Values");

        //List of Strings meant to work as intervals on the X axis for the bar graph, though not working at the moment
        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("Jan");
        theDates.add("Feb");
        theDates.add("Mar");
        theDates.add("Apr");
        theDates.add("May");
        theDates.add("Jun");


        //Issue: BarData function does not take in string array lists and when creating a data set it doesn't allow string values
        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);

        //This section "renames" the x values on the chart that were added with the data to strings that were created in the theDates array
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(filteredDates));
         */

        // wires region dropdown menu
        spinnerRegion = findViewById(R.id.spinner_region_graph);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allCountries);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(adapter1);
        spinnerRegion.setOnItemSelectedListener(this);

        // wires data type dropdown menu
        spinnerDataType = findViewById(R.id.spinner_data_type_graph);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDataType.setAdapter(adapter2);
        spinnerDataType.setOnItemSelectedListener(this);

        // wires time period dropdown menu
        spinnerDate = findViewById(R.id.spinner_time_period_graph);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.dates, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapter3);
        spinnerDate.setOnItemSelectedListener(this);
    }

    /**
     * Navigates to article
     * @param view
     */
    public void sendToArticleActivity(View view) {
        Intent iArticles = new Intent(GraphActivity.this, ArticlesActivity.class);
        startActivity(iArticles);
    }

    /**
     * Navigates to main
     * @param view
     */
    public void sendToMainActivity(View view) {
        Intent iMain = new Intent(GraphActivity.this, MainActivity.class);
        startActivity(iMain);
    }

    @Override
    // event listener runs when one of the options from the drop downs is selected
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String dataType = spinnerDataType.getSelectedItem().toString();
        String date = spinnerDate.getSelectedItem().toString();
        String region = spinnerRegion.getSelectedItem().toString();
        int totalDays;
        float graphXValue = 0;
        ArrayList<Integer> dayX = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        //Conditional to be sure that all items from each drop down are selected
        if (!dataType.equals("Data Type") && !date.equals("Date") && !region.equals("Region")) {
            //Used to determine the amount of days/rows of data are collected to be displayed on the bar graph
            // Log.i("Parameters working", "working");
            if (date.equals("Past Day")) {
                totalDays = 1;
            } else if (date.equals("Past Week")) {
                totalDays = 7;
            } else if (date.equals("Past Month")) {
                totalDays = 30;
            } else if (date.equals("Past 6 Months")) {
                totalDays = 180;
            } else if (date.equals("Past Year")) {
                totalDays = 365;
            } else {
                totalDays = 1000;
            }
            dayValue.clear();
            filteredDates.clear();

            //Conditional used to separate processes to gathering vaccination or case data
            if (dataType.equals("Vaccinations")) {
                int rowNum = MainActivity.getVaccinationSample().size();
                // Log.i("Graph values working", "working");
                for( int i = rowNum - 1; i >= 0; i-- ) {
                    if (totalDays > 0) {
                        VaccinationSample sample = MainActivity.getVaccinationSample().get(i);
                        String sampleCountry = sample.getCountry();
                        String dateString = sample.getDate();
                        //If the country on a row is equal to the country being looked for, the
                        if (sampleCountry.equals(region)) {
                            filteredDates.add(0, dateString);
                            graphXValue++;
                            totalDays--;
                            String VacValue = sample.getDailyVac();
                            float VacFloat = Float.parseFloat(VacValue);
                            dayX.add(0, totalDays);
                            dayValue.add(0, VacFloat);
                        }
                    }
                }
            } else {
                //Runs the same code as the vaccination conditional but gathering information from the cases data set instead
                // Log.i("cases values working", "working");
                int rowNum = MainActivity.getCaseSample().size();
                for( int i = rowNum - 1; i >= 0; i-- ) {
                    if (totalDays > 0) {
                        CaseSample sample = MainActivity.getCaseSample().get(i);
                        String sampleCountry = sample.getCountry();
                        String dateString = sample.getDate();
                        if (sampleCountry.equals(region)) {
                            filteredDates.add(0, dateString);
                            graphXValue++;
                            String CaseValue = sample.getDailyNew();
                            float CaseFloat = Float.parseFloat(CaseValue);
                            dayX.add(0, totalDays);
                            dayValue.add(0, CaseFloat);
                            totalDays--;
                        }
                    }
                }
            }
            if (dayX.get(0) != 1) {
                for (int i = 0; i < dayX.size(); i++) {
                    dayX.remove(i);
                    dayX.add(i, i + 1);
                }
            }

            for (int i = 0; i <dayX.size(); i++) {
                // Log.d("array", dayX.toString());
                // Log.d("array", dayValue.toString());
                float dayFloat = (float) dayX.get(i);
                barEntries.add(new BarEntry(dayFloat, dayValue.get(i)));
            }
            // Log.d("hi", barEntries.toString());
            BarDataSet barDataSet = new BarDataSet(barEntries, "Values");
            BarData theData = new BarData(barDataSet);
            barChart.setData(theData);
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(filteredDates));
            barChart.invalidate();


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Event listener used for when the user highlights a bar on the chart
    private OnChartValueSelectedListener chartListener = new OnChartValueSelectedListener() {
        //When a bar is highlighted, its information is displayed on a button that can be clicked
        public void onValueSelected(Entry e, Highlight h) {
            // Log.d("working listener", dayValue.toString());
            Button buttonDetails = findViewById(R.id.button_details_graph);
            buttonDetails.setVisibility(View.VISIBLE);
            buttonDetails.setX(h.getXPx() - 125);
            buttonDetails.setY(h.getYPx() + 200);
            int floatIndex = Math.round(h.getX() - 1);
            String displayedText = "Value: " + h.getY() + "\n Date: " + filteredDates.get(floatIndex) + "\n CLick For More Info";
            dateStringView = filteredDates.get(floatIndex);
            buttonDetails.setText(displayedText);
        }

        @Override
        public void onNothingSelected() {
            //When the bar is deselected, the button displaying information is hidden
            Button buttonDetails = findViewById(R.id.button_details_graph);
            buttonDetails.setVisibility(View.INVISIBLE);
        }
    };

    // getter
    public static String getDateStringView() {
        return dateStringView;
    }
}

