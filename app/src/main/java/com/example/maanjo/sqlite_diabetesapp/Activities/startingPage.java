package com.example.maanjo.sqlite_diabetesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.maanjo.sqlite_diabetesapp.Database.DiabetesMemoDataSource;
import com.example.maanjo.sqlite_diabetesapp.R;

public class startingPage extends AppCompatActivity{

    public static final String LOG_TAG = logIn.class.getSimpleName();
    private TextView mTextMessage;
    private DiabetesMemoDataSource dataSource;
    public EditText editTextBloodSugar;
    public String userName;
    public Button buttonEingabe;
    public Spinner feeling_spinner;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        dataSource = new DiabetesMemoDataSource(this);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

        mTextMessage = (TextView) findViewById(R.id.greetings);
        mTextMessage.setText("Hey, " + getIntent().getStringExtra("userString"));
        userName = getIntent().getStringExtra("userString");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       activateEnterButton();
    }

    protected void onResume() {

        super.onResume();
        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();
    }

    protected void onPause() {

        super.onPause();
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    private void activateEnterButton(){

        buttonEingabe = findViewById(R.id.button_eingabe);
        feeling_spinner = findViewById(R.id.feeling_spinner);
        editTextBloodSugar = findViewById(R.id.blutzucker_input);

        buttonEingabe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String feeling = feeling_spinner.getSelectedItem().toString();
                String bloodSugarStr = editTextBloodSugar.getText().toString();
                int bloodSugar = Integer.parseInt(bloodSugarStr);
                int userId = dataSource.getUserId(getIntent().getStringExtra("userString"));

                dataSource.createBloodValue(bloodSugar,feeling, userId);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            String userString = getIntent().getStringExtra("userString");

            switch (item.getItemId()) {
                case R.id.navigation_start:

                    startActivity(new Intent(startingPage.this, startingPage.class));
                    return true;

                case R.id.navigation_table:

                    startActivity(new Intent(startingPage.this, tableView.class).putExtra("userString", userString));
                    return true;

                case R.id.navigation_graph:

                    startActivity(new Intent(startingPage.this, graphView.class).putExtra("userString", userString));
                    return true;
            }
            return false;
        }
    };
}
