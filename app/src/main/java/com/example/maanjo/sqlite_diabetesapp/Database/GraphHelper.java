package com.example.maanjo.sqlite_diabetesapp.Database;

import android.content.Context;

import com.example.maanjo.sqlite_diabetesapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Hilfsklasse zur Erstellung eines Graphen für die Activity GraphViewer
 * Zuhilfe gezogene graph plotting library:
 * @Link http://www.android-graphview.org/
 */
public class GraphHelper {

    Context c;
    private Long minX;
    private Long maxX;

    /**
     * Konstruktor
     * Initialisiert den Kontext
     *
     * @param c: Anwendungskontext
     */
    public GraphHelper(Context c){

        this.c = c;
    }

    /**
     * Getter Methoden der niedrigsten X-Koordinate
     *
     * @return Niedrigste X-Koordinate
     */
    public Long getMinX(){

        return this.minX;
    }

    /**
     * Getter Methoden der höchsten X-Koordinate
     *
     * @return Höchste X-Koordinate
     */
    public Long getMaxX(){

        return this.maxX;
    }

    /**
     *Auslesen aller Einträge der Tabelle Metrics mit der übergebenen UserId und überführen in
     * LineGraphSeries<DataPoint>-Format, die von einem Graph übernommen werden kann.
     *
     * @param userId: Übergebene UserID
     * @return Der UserId entsprechende Einträge der Tabelle Metrics in LineGraphSeries<DataPoint>-Form
     */
   public LineGraphSeries<DataPoint> getGraphData(int userId){

        ArrayList<BloodValue> bloodValues = new DiabetesMemoDataSource(c).getAllBloodValue(userId);
        BloodValue b;
        DataPoint[] dataPoints = new DataPoint[bloodValues.size()];


        for(int i = 0; i < bloodValues.size(); i++){

            b = bloodValues.get(i);

            int bloodSugar = b.getBlood_sugar();
            Long date_milSec = b.getTime();
            Date resultDate = new Date(date_milSec);

            dataPoints[i] = new DataPoint(resultDate, bloodSugar);

            if(i == 0){

                minX = date_milSec;
            }
            if(i == bloodValues.size()-1){
                maxX = date_milSec;
            }

        }

        LineGraphSeries<DataPoint> data = new LineGraphSeries(dataPoints);

        return data;
    }
}
