package com.example.maanjo.sqlite_diabetesapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private UserDataSource dataSource;
    public EditText editTextName;
    public EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextName.setOnClickListener(this);
        editTextPassword.setOnClickListener(this);

        dataSource = new UserDataSource(this);

        activateLogInButton();
    }

    protected void onResume() {

        super.onResume();
        dataSource.open();
    }

    protected void onPause() {

        super.onPause();
        dataSource.close();
    }

    private void activateLogInButton(){

        Button buttonLogIn = (Button) findViewById(R.id.button_logIn);
        final EditText editTextUsername = (EditText) findViewById(R.id.editText_name);
        final EditText editTextPassword = (EditText) findViewById(R.id.editText_password);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userString = editTextUsername.getText().toString();
                String passwordString = editTextPassword.getText().toString();

                if(TextUtils.isEmpty(userString)){

                    editTextUsername.setError(getString(R.string.errorMessage));
                    return;
                }
                if(TextUtils.isEmpty(passwordString)){

                    editTextUsername.setError(getString(R.string.errorMessage));
                    return;
                }

                dataSource.createUser(userString, passwordString);
            }
        });

    }

    public void onClick(View v){

        editTextName.setText("");
        editTextPassword.setText("");
    }


}
