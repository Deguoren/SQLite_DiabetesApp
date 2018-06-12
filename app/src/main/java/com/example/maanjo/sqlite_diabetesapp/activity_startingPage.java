package com.example.maanjo.sqlite_diabetesapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class activity_startingPage extends AppCompatActivity {

    public static final String LOG_TAG = activity_logIn.class.getSimpleName();
    private TextView mTextMessage;
    private DiabetesMemoDataSource dataSource;
    public EditText editTextFeeling;
    public EditText editTextBloodSugar;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        dataSource = new DiabetesMemoDataSource(this);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

        mTextMessage = (TextView) findViewById(R.id.greetings);
        mTextMessage.setText("Hey, " + getIntent().getStringExtra("userString"));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       // activateLogInButton();
    }

   /* private void activateLogInButton(){

        Button buttonLogIn = findViewById(R.id.button_eingabe);
        editTextFeeling = findViewById(R.id.editText_nameLogIn);
        editTextBloodSugar = findViewById(R.id.editText_passwordLogIn);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String feeling = editTextFeeling.getText().toString();
                String bloodSugarStr = editTextBloodSugar.getText().toString();
                int bloodSugar = Integer.parseInt(bloodSugarStr);




            }
        });

    }
*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_start:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_table:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_graph:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
}
