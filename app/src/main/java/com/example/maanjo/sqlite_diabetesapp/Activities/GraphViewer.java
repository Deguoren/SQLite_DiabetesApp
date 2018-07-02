package com.example.maanjo.sqlite_diabetesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.maanjo.sqlite_diabetesapp.Database.DiabetesMemoDataSource;
import com.example.maanjo.sqlite_diabetesapp.Database.GraphHelper;
import com.example.maanjo.sqlite_diabetesapp.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GraphViewer verwaltet die Funktionalitäten der Oberfläche data_overview_graph
 *
 */
public class GraphViewer extends AppCompatActivity {

    public static final String LOG_TAG = LogIn.class.getSimpleName();
    private DiabetesMemoDataSource dataSource;
    GraphHelper graphHelper;
    com.jjoe64.graphview.GraphView line_graph;
    LineGraphSeries<DataPoint> data_series;
    String userName;
    public int userId;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd.MM");

    /**
     * OnCreate-Methode der Klasse GraphViewer
     * Referenziert die Klasse zum Layout und öffnet die Datenbankverbindung
     * Übernimmt die Daten aus der vorherigen Activity und benutzt diese, um dem Graph Werte zuzuweisen
     * Ist zuständig für die Formatierung des Graphen
     *
     * @param savedInstanceState: Gespeicherter Zustand der Activity
     */
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

        line_graph.getGridLabelRenderer().setHorizontalAxisTitle("Zeitpunkt");
        line_graph.getGridLabelRenderer().setVerticalAxisTitle("Blutzucker");

        line_graph.getViewport().setXAxisBoundsManual(true);
        line_graph.getViewport().setMinX(graphHelper.getMinX());
        line_graph.getViewport().setMaxX(graphHelper.getMaxX());

        line_graph.getGridLabelRenderer().setHumanRounding(false);
        line_graph.getViewport().setScrollable(true);


        BottomNavigationView navigation = findViewById(R.id.navigation3);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Öffnet Verbindung zur Datenbank, wenn Activity erneute geöffnet wird
     */
    protected void onResume() {

        super.onResume();
        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

    }

    /**
     * Schließt bestehende Verbindung zur Datenbank, wenn die Activity pausiert wird
     */
    protected void onPause() {

        super.onPause();
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    //Hinzufügen eines Listeners zu dem BottomNavigationView (Menü)
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * Anonyme onNavigationItemSelected-Listener Klasse
         * Fügt den einzelnen Menüfeldern eine Funktionalität hinzu
         * Hier: Navigieren zwischen den Activities Graph, Tabelle und Startseite
         *
         * @param item: Ausgewähltes Element des Menüs
         * @return True oder False, abhängig davon, ob ein Element ausgewählt wurde
         */
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            bundle.putString("userName", userName);
            Intent table_intent = new Intent();

            switch (item.getItemId()) {
                case R.id.navigation_start3:

                    //Activity StartingPage wird aufgerufen und der UserName als Parameter mitgegeben
                    startActivity(new Intent(GraphViewer.this, StartingPage.class).putExtra("userString", userName));
                    return true;

                case R.id.navigation_table3:

                    //Activity TableViewer wird aufgerufen und der UserName und die UserId werden mitgegeben
                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.TableViewer");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;

                case R.id.navigation_graph3:

                    //Activity GraphViewer wird aufgerufen und der UserName und die UserId werden mitgegeben
                    table_intent.setClassName("com.example.maanjo.sqlite_diabetesapp", "com.example.maanjo.sqlite_diabetesapp.Activities.GraphViewer");
                    table_intent.putExtras(bundle);
                    startActivity(table_intent);
                    return true;
            }
            return false;
        }
    };
}

