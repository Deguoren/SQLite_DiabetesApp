package com.example.maanjo.sqlite_diabetesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maanjo.sqlite_diabetesapp.Database.BloodValue;
import com.example.maanjo.sqlite_diabetesapp.Database.DiabetesMemoDataSource;
import com.example.maanjo.sqlite_diabetesapp.Database.GraphHelper;
import com.example.maanjo.sqlite_diabetesapp.Database.TableHelper;
import com.example.maanjo.sqlite_diabetesapp.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import static java.lang.reflect.Array.getInt;

public class graphView extends AppCompatActivity {

    public static final String LOG_TAG = logIn.class.getSimpleName();
    private DiabetesMemoDataSource dataSource;
    GraphHelper graphHelper;
    GraphView line_graph;
    LineGraphSeries<DataPoint> data_series;
    String userName;
    public int userId;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd.MM");

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_overview_graph);
        dataSource = new DiabetesMemoDataSource(this);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId", 0);
        userName = bundle.getString("userName");

        graphHelper = new GraphHelper(this);
        line_graph = findViewById(R.id.graph);
        data_series = graphHelper.getGraphData(userId);
        line_graph.addSeries(data_series);

        line_graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

            public String formatLabel(double value, boolean isValueX){

                if(isValueX){
                    return sdf.format((new Date((long)value)));
                }else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation3);
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
            bundle.putString("userName", userName);
            Intent table_intent = new Intent();

            switch (item.getItemId()) {
                case R.id.navigation_start3:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.startingPage");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;

                case R.id.navigation_table3:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.tableView");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;

                case R.id.navigation_graph3:

                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.graphView");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;
            }
            return false;
        }
    };
}

