package com.example.maanjo.sqlite_diabetesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.maanjo.sqlite_diabetesapp.Database.DiabetesMemoDataSource;
import com.example.maanjo.sqlite_diabetesapp.Database.TableHelper;
import com.example.maanjo.sqlite_diabetesapp.R;

import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TableViewer extends AppCompatActivity {

    public static final String LOG_TAG = LogIn.class.getSimpleName();
    private DiabetesMemoDataSource dataSource;
    public de.codecrafters.tableview.TableView<String[]> tv;
    public TableHelper tableHelper;
    public String userName;
    public int userId;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_overview_table);
        dataSource = new DiabetesMemoDataSource(this);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId", 0);
        userName = bundle.getString("userName");

        tableHelper = new TableHelper(this);
        tv = findViewById(R.id.tableView);
        tv.setColumnCount(3);
        tv.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableHelper.getTableHeader()));
        tv.setDataAdapter(new SimpleTableDataAdapter(this, tableHelper.getBloodValue(userId)));

        BottomNavigationView navigation = findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            bundle.putString("userName", String.valueOf(userName));
            Intent table_intent = new Intent();

            switch (item.getItemId()) {
                case R.id.navigation_start2:

                    startActivity(new Intent(TableViewer.this, StartingPage.class).putExtra("userString", userName));
                    return true;

                case R.id.navigation_table2:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.TableViewer");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;

                case R.id.navigation_graph2:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.GraphViewer");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;
            }
            return false;
        }
    };
}

