package com.example.maanjo.sqlite_diabetesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class tableView extends AppCompatActivity {

    public static final String LOG_TAG = logIn.class.getSimpleName();
    private DiabetesMemoDataSource dataSource;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        dataSource = new DiabetesMemoDataSource(this);
        String userString = getIntent().getStringExtra("userString");
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

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

    //showAllListEntries();

    private void showAllListEntries () {
        List<BloodValue> shoppingMemoList = dataSource.getAllBloodValue();

        ArrayAdapter<BloodValue> shoppingMemoArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                shoppingMemoList);

        ListView shoppingMemosListView = (ListView) findViewById(R.id.listview_shopping_memos);
        shoppingMemosListView.setAdapter(shoppingMemoArrayAdapter);
    }




}

