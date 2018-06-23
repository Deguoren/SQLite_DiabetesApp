package com.example.maanjo.sqlite_diabetesapp.Database;

import android.content.Context;

import com.example.maanjo.sqlite_diabetesapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GraphHelper {

    Context c;
    private Long minX;
    private Long maxX;

    public GraphHelper(Context c){

        this.c = c;
    }

    public Long getMinX(){

        return this.minX;
    }

    public Long getMaxX(){

        return this.maxX;
    }

   public LineGraphSeries<DataPoint> getGraphData(int userId){

        ArrayList<BloodValue> bloodValues = new DiabetesMemoDataSource(c).getAllBloodValue(userId);
        BloodValue b;
        DataPoint[] dataPoints = new DataPoint[bloodValues.size()];


        for(int i = 0; i < bloodValues.size(); i++){

            b = bloodValues.get(i);

            int bloodSugar = b.getBlood_sugar();
            Long date_milSec = b.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
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
