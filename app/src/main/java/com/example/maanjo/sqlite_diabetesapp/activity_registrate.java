package com.example.maanjo.sqlite_diabetesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_registrate extends AppCompatActivity{

    private DiabetesMemoDataSource dataSource;
    public EditText editTextName;
    public EditText editTextPassword;
    public EditText editTextPassword2;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        dataSource = new DiabetesMemoDataSource(this);
        dataSource.open();
        activateRegButton();

    }

    public void activateRegButton(){

        Button buttonReg = findViewById(R.id.button_letsgo);
        editTextName = findViewById(R.id.editText_nameReg);
        editTextPassword = findViewById(R.id.editText_passwordReg);
        editTextPassword2 = findViewById(R.id.editText_passwordReg2);

       buttonReg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                String userString = editTextName.getText().toString();
                String passwordString = editTextPassword.getText().toString();
                String passwordConfirmString = editTextPassword2.getText().toString();

                if(passwordString.equals(passwordConfirmString)) {

                    dataSource.createUser(userString, passwordString);
                    startActivity(new Intent(activity_registrate.this, activity_logIn.class));
                    return;

                }else{

                    editTextPassword2.setError(getString(R.string.errorMessageSecPw));
                    return;
                }
            }
        });
    }
}
