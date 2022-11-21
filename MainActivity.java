package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.lang.*;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // private static instance variables containing VaccinationSample and CaseSample objects
    private static ArrayList<VaccinationSample> vaccinationSample = new ArrayList<>();
    private static ArrayList<CaseSample> caseSample = new ArrayList<>();
    private static boolean alreadyUsed = false; // makes sure CSV does not get reprocessed each time Main is opened

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!alreadyUsed) {
            // processing CSV files
            try {
                readVaccinationData();
                alreadyUsed = true;
            } catch (IOException e) {
                Log.wtf("MainActivity", "Error", e);
                e.printStackTrace();
            }

            try {
                readCaseData();
                alreadyUsed = true;
            } catch (IOException e) {
                Log.wtf("MainActivity", "Error", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads CSV data in country_vaccinations file
     *
     * Precondition: country_vaccinations file exists
     * Postcondition: vaccinationSample ArrayList is populated with values from the CSV
     *
     * @throws IOException
     */
    private void readVaccinationData() throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource
                (R.raw.country_vaccinations)));
        List<String[]> vaccinations = reader.readAll(); // reads CSV data into a List of String[]
        for(int i = 1; i < vaccinations.size(); i++) {
            String[] row = vaccinations.get(i); // local variable to store each record
            for(int j = 0; j < row.length; j++){
                if (row[j].length() == 0)
                    row[j] = "0.0"; // if no data between commas, set that element to 0.0
            }
            vaccinationSample.add(new VaccinationSample(row[0], row[1], row[2], row[3], row[4],
                    row[5], row[6], row[7], row[8], row[9], row[10], row[11], row[12], row[13],
                    row[14])); // constructs new VaccinationSample object and adds to ArrayList
            // Log.d("MainActivity", "Line: " + vaccinationSample.get(i-1).getDate() + ", " +
            // vaccinationSample.get(i-1).getCountry()); // debugging code
        }
    }

    /**
     * Reads CSV data in worldometer_coronavirus_daily_data file
     *
     * Precondition: worldometer_coronavirus_daily_data file exists
     * Postcondition: caseSample ArrayList is populated with values from the CSV
     *
     * @throws IOException
     */
    private void readCaseData() throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource
                (R.raw.worldometer_coronavirus_daily_data)));;
        List<String[]> cases = reader.readAll();
        for(int i = 1; i < cases.size(); i++) {
            String[] row = cases.get(i);
            for(int j = 0; j < row.length; j++) {
                if (row[j].length() == 0)
                    row[j] = "0.0";
            }
            if(row.length >= 7) {
                caseSample.add(new CaseSample(row[0], row[1], row[2], row[3], row[4], row[5], row[6]));
            }
            else { // some records have an empty last column, which is corrected by the following
                caseSample.add(new CaseSample(row[0], row[1], row[2], row[3], row[4], row[5],
                        "0.0"));
            }
            // Log.d("MainActivity", "Line: " + caseSample.get(i-1).getDate() + ", " +
            // caseSample.get(i-1).getCountry()); // debugging code
        }
    }

    /**
     * Navigates to graph
     * @param view
     */
    public void sendToGraphActivity(View view) {
        Intent iGraph = new Intent(MainActivity.this, GraphActivity.class);
        startActivity(iGraph);
    }

    /**
     * Navigates to map
     * @param view
     */
    public void sendToMapActivity(View view) {
        Intent iMap = new Intent(MainActivity.this, MapActivity.class);
        startActivity(iMap);
    }

    /**
     * Navigates to info
     * @param view
     */
    public void sendToInfoActivity(View view) {
        Intent iInfo = new Intent(MainActivity.this, InfoActivity.class);
        startActivity(iInfo);
    }

    /**
     * Getter for vaccinationSample
     * @return vaccinationSample ArrayList
     */
    public static ArrayList<VaccinationSample> getVaccinationSample() {
        return vaccinationSample;
    }

    /**
     * Getter for caseSample
     * @return caseSample ArrayList
     */
    public static ArrayList<CaseSample> getCaseSample() {
        return caseSample;
    }
}