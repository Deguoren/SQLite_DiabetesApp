package com.example.maanjo.sqlite_diabetesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maanjo.sqlite_diabetesapp.Database.BloodValue;
import com.example.maanjo.sqlite_diabetesapp.Database.DiabetesMemoDataSource;
import com.example.maanjo.sqlite_diabetesapp.Database.TableHelper;
import com.example.maanjo.sqlite_diabetesapp.R;

import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class tableView extends AppCompatActivity {

    public static final String LOG_TAG = logIn.class.getSimpleName();
    private DiabetesMemoDataSource dataSource;
    TableView<String[]> tv;
    TableHelper tableHelper;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_overview_table);
        dataSource = new DiabetesMemoDataSource(this);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);

        tableHelper = new TableHelper(this);
        tv = findViewById(R.id.tableView);
        tv.setColumnCount(3);
        tv.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getTableHeader()));
        tv.setDataAdapter(new SimpleTableDataAdapter(this, tableHelper.getBloodValue(userId)));

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
}

