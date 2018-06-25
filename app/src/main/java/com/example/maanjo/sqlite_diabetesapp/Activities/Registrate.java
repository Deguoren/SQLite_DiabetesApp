package com.example.maanjo.sqlite_diabetesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.maanjo.sqlite_diabetesapp.Database.DiabetesMemoDataSource;
import com.example.maanjo.sqlite_diabetesapp.R;

public class Registrate extends AppCompatActivity{

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
                    startActivity(new Intent(Registrate.this, LogIn.class));
                    return;

                }else{

                    editTextPassword2.setError(getString(R.string.errorMessageSecPw));
                    return;
                }
            }
        });
    }
}
