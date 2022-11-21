package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // private instance variables
    private Spinner spinnerDataType;
    private Spinner spinnerTimePer;
    private TextView countryInfo;
    private ConstraintLayout screenMap;
    private LinearLayout linLayout;

    // private static instance variables
    private static String dataType;
    private static String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // sets up data type dropdown menu
        spinnerDataType = findViewById(R.id.spinner_data_type_map);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDataType.setAdapter(adapter1);
        spinnerDataType.setOnItemSelectedListener(this);

        // sets up date dropdown menu
        spinnerTimePer = findViewById(R.id.spinner_date_map);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.dates, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimePer.setAdapter(adapter2);
        spinnerTimePer.setOnItemSelectedListener(this);

        // wires screen
        screenMap = findViewById(R.id.screen_map);
        linLayout = findViewById(R.id.image_button_layout);

        // wires country info textview
        countryInfo = findViewById(R.id.textView_country_info);

    }

    /**
     * Navigates to articles
     * @param view
     */
    public void sendToArticleActivity(View view) {
        Intent iArticles = new Intent(MapActivity.this, ArticlesActivity.class);
        startActivity(iArticles);
    }

    /**
     * Navigates to main
     * @param view
     */
    public void sendToMainActivity(View view) {
        Intent iMain = new Intent(MapActivity.this, MainActivity.class);
        startActivity(iMain);
    }

    /**
     * Runs when an item is selected from dropdown
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // stores user-selected values from the spinners
        dataType = spinnerDataType.getSelectedItem().toString();
        date = spinnerTimePer.getSelectedItem().toString();

        // totalDays stores the number of records to be used for visualization, depending on time period chosen
        int totalDays = 1;
        if (!dataType.equals("Data Type") && !date.equals("Date")) { // when non-default values are selected
            // Log.i("Parameters", "Working"); // debugging
            if (date.equals("Past Week"))
                totalDays = 7;
            else if (date.equals("Past Month"))
                totalDays = 30;
            else if (date.equals("Past 6 Months"))
                totalDays = 180;
            else if (date.equals("Past Year"))
                totalDays = 365;
            else
                totalDays = 1000;

            if (dataType.equals("Vaccinations")) {
                // populates a list with all countries
                int row = MainActivity.getVaccinationSample().size(); // stores the size of vaccinationSample
                ArrayList<String> allCountriesVac = new ArrayList<>(); // stores all unique countries
                for (int i = 0; i < row - 1; i++) {
                    VaccinationSample sample = MainActivity.getVaccinationSample().get(i); // stores current record
                    String sampleCountry = sample.getCountry(); // stores country of current record
                    boolean isFound = false; // stores boolean that determines if country is already in allCountriesVac
                    for (int j = 0; j < allCountriesVac.size(); j++) {
                        if (allCountriesVac.get(j).equals(sampleCountry)) { // checks if sampleCountry already exists in list
                            isFound = true;
                        }
                    }
                    if (!isFound) { // if sampleCountry does not exist in list
                        allCountriesVac.add(sampleCountry);
                    }
                }

                // populates a list with cumulative number of vaccinations
                ArrayList<Integer> totalValues = new ArrayList<>(); // stores total values for each country within specified time period
                for (int i = 0; i < allCountriesVac.size(); i++) {
                    String currentCountry = allCountriesVac.get(i); // stores current country
                    int totalValue = 0; // initializes totalValue
                    int currentDays = totalDays; // initializes currentDays to the number of days needed

                    for (int j = row - 1; j >= 0; j--) {
                        if (currentDays > 0) {
                            VaccinationSample sample = MainActivity.getVaccinationSample().get(i); // stores current record
                            String sampleCountry = sample.getCountry(); // stores current country
                            if (sampleCountry.equals(currentCountry)) {
                                String vacValue = sample.getDailyVac(); // gets vaccination numbers
                                int vacInt = (int) Float.parseFloat(vacValue);// parses vaccinations into integer
                                totalValue = totalValue + (int) vacInt; // adds to totalValue
                                currentDays--; // decrements counter
                            }
                        }
                    }
                    totalValues.add(totalValue); // adds a record for each country
                }

                for (String s : allCountriesVac) {
                    if (s.equals("Argentina")) { // checks to see if current country is equal to target
                        // creates, sets position, and formats image button
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(250f);
                        imageButton.setY(885f);
                        // runs onClick when image button is clicked
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // sets text in countryInfo textview to value corresponding with country
                                countryInfo.setText("Argentina\n" + totalValues.get(147) + " vaccinations");
                            }
                        });
                    } else if (s.equals("Brazil")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(235f);
                        imageButton.setY(795f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Brazil\n" + totalValues.get(134) + " vaccinations");
                            }
                        });
                    } else if (s.equals("Canada")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(100f);
                        imageButton.setY(531f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Canada\n" + totalValues.get(131) + " vaccinations");
                            }
                        });
                    } else if (s.equals("Chile")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(135f);
                        imageButton.setY(856f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Chile\n" + totalValues.get(129) + " vaccinations");
                            }
                        });
                    } else if (s.equals("China")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(700f);
                        imageButton.setY(624f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("China\n" + totalValues.get(128) + " vaccinations");
                            }
                        });
                    } else if (s.equals("Colombia")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(80f);
                        imageButton.setY(750f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Colombia\n" + totalValues.get(127) + " vaccinations");
                            }
                        });
                    } else if (s.equals("Mexico")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(-68f);
                        imageButton.setY(675f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Mexico\n" + totalValues.get(77) + " vaccinations");
                            }
                        });
                    } else if (s.equals("United States")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.blue_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(-60f);
                        imageButton.setY(620f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("United States\n" + totalValues.get(5) + " vaccinations");

                            }
                        });
                    }
                    // image button for final country in country list or first country in csv
                    ImageButton imageButton = new ImageButton(this);
                    imageButton.setImageResource(R.drawable.blue_circle);
                    imageButton.setBackgroundColor(0);
                    imageButton.setX(682f);
                    imageButton.setY(627f);
                    screenMap.addView(imageButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            countryInfo.setText("Afghanistan\n" + totalValues.get(154) + " vaccinations");
                        }
                    });
                }
            }
            else {
                int row = MainActivity.getCaseSample().size();
                ArrayList<String> allCountriesCase = new ArrayList<>();
                for (int i = 0; i < row - 1; i++) {
                    CaseSample sample = MainActivity.getCaseSample().get(i);
                    String sampleCountry = sample.getCountry();
                    boolean isFound = false;
                    for (int j = 0; j < allCountriesCase.size(); j++) {
                        if (allCountriesCase.get(j).equals(sampleCountry)) {
                            isFound = true;
                        }
                    }
                    if (!isFound) {
                        allCountriesCase.add(sampleCountry);
                    }
                }

                ArrayList<Integer> totalValues = new ArrayList<>();
                for (int i = 0; i < allCountriesCase.size(); i++) {
                    String currentCountry = allCountriesCase.get(i);
                    int totalValue = 0;
                    int currentDays = totalDays;

                    for (int j = row - 1; j >= 0; j--) {
                        if (currentDays > 0) {
                            CaseSample sample = MainActivity.getCaseSample().get(i);
                            String sampleCountry = sample.getCountry();
                            if (sampleCountry.equals(currentCountry)) {
                                String caseValue = sample.getDailyNew();
                                // Log.d("Processed ", caseValue + " for " + sampleCountry);
                                int caseInt = (int) Float.parseFloat(caseValue);
                                totalValue = totalValue + (int) caseInt;
                                currentDays--;
                            }
                        }
                    }
                    totalValues.add(totalValue);
                }

                for(String s: allCountriesCase) {
                    if(s.equals("Argentina")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(250f);
                        imageButton.setY(885f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Argentina\n" + totalValues.get(147) + " vaccinations");
                            }
                        });
                    }
                    else if(s.equals("Brazil")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(235f);
                        imageButton.setY(795f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Brazil\n" + totalValues.get(134) + " vaccinations");
                            }
                        });
                    }
                    else if(s.equals("Canada")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(100f);
                        imageButton.setY(531f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Canada\n" + totalValues.get(131) + " vaccinations");
                            }
                        });
                    }
                    else if(s.equals("Chile")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(135f);
                        imageButton.setY(856f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Chile\n" + totalValues.get(129) + " vaccinations");
                            }
                        });
                    }
                    else if(s.equals("China")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(700f);
                        imageButton.setY(624f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("China\n" + totalValues.get(128) + " vaccinations");
                            }
                        });
                    }
                    else if(s.equals("Colombia")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(80f);
                        imageButton.setY(750f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Colombia\n" + totalValues.get(127) + " vaccinations");
                            }
                        });
                    }
                    else if (s.equals("Mexico")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(-68f);
                        imageButton.setY(675f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("Mexico\n" + totalValues.get(77) + " vaccinations");
                            }
                        });
                    }
                    else if (s.equals("USA")) {
                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setImageResource(R.drawable.red_circle);
                        imageButton.setBackgroundColor(0);
                        linLayout.addView(imageButton);
                        imageButton.setX(-60f);
                        imageButton.setY(620f);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                countryInfo.setText("United States\n" + totalValues.get(5) + " vaccinations");
                            }
                        });
                    }
                }
                ImageButton imageButton = new ImageButton(this);
                imageButton.setImageResource(R.drawable.red_circle);
                imageButton.setBackgroundColor(0);
                imageButton.setX(682f);
                imageButton.setY(627f);
                screenMap.addView(imageButton);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countryInfo.setText("Afghanistan\n" + totalValues.get(154) + " vaccinations");
                    }
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Getter for dataType
     * @return dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Getter for date
     * @return date
     */
    public String getDate() {
        return date;
    }
}