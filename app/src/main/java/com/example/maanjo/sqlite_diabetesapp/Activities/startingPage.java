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
    public int userId;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        dataSource = new DiabetesMemoDataSource(this);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

        mTextMessage = findViewById(R.id.greetings);
        String name = getIntent().getStringExtra("userString");
        mTextMessage.setText(new StringBuilder().append("Hey, ").append(name).toString());
        userName = getIntent().getStringExtra("userString");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       activateEnterButton();
    }

    protected void onResume() {

        super.onResume();
        mTextMessage = findViewById(R.id.greetings);
        String name = getIntent().getStringExtra("userString");
        mTextMessage.setText(new StringBuilder().append("Hey, ").append(name).toString());
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
                userId = dataSource.getUserId(getIntent().getStringExtra("userString"));

                dataSource.createBloodValue(bloodSugar,feeling, userId);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            userId = dataSource.getUserId(getIntent().getStringExtra("userString"));
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            bundle.putString("userName", userName);
            Intent table_intent = new Intent();

            switch (item.getItemId()) {
                case R.id.navigation_start:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.startingPage");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;

                case R.id.navigation_table:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.tableView");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;

                case R.id.navigation_graph:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.graphView");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;
            }
            return false;
        }
    };
}
