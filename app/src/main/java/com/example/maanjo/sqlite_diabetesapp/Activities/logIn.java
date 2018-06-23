package com.example.maanjo.sqlite_diabetesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.maanjo.sqlite_diabetesapp.Database.DiabetesMemoDataSource;
import com.example.maanjo.sqlite_diabetesapp.R;

public class logIn extends AppCompatActivity{

    public static final String LOG_TAG = logIn.class.getSimpleName();
    private DiabetesMemoDataSource dataSource;
    public EditText editTextName;
    public EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        dataSource = new DiabetesMemoDataSource(this);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

        editTextName = findViewById(R.id.editText_nameLogIn);
        editTextPassword = findViewById(R.id.editText_passwordLogIn);

        activateLogInButton();
        activateRegButton();
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

    private void activateLogInButton(){

        Button buttonLogIn = findViewById(R.id.button_logIn);
        editTextName = findViewById(R.id.editText_nameLogIn);
        editTextPassword = findViewById(R.id.editText_passwordLogIn);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String userString = editTextName.getText().toString();
                String passwordString = editTextPassword.getText().toString();

                if(TextUtils.isEmpty(userString)){

                    editTextName.setError(getString(R.string.errorMessage));
                    return;
                }
                if(TextUtils.isEmpty(passwordString)){

                    editTextPassword.setError(getString(R.string.errorMessage));
                    return;
                }

                if(dataSource.checkUserName(userString) && dataSource.checkPassword(passwordString)){


                    startActivity(new Intent(logIn.this, startingPage.class).putExtra("userString", userString));
                }
                else{
                    editTextName.setError(getString(R.string.wrongLogIn));
                    return;
                }

            }
        });

    }

    public void activateRegButton(){

        Button buttonReg = findViewById(R.id.button_reg);
        buttonReg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                startActivity(new Intent(logIn.this, Registrate.class));
            }
        });
    }

}
