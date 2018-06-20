package com.example.maanjo.sqlite_diabetesapp.Database;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TableHelper {

    Context c;

    private String[] tableHeader = {"Zeit", "Blutwert", "Gefühlslage"};
    private String[][] bloodValueArr;

    public TableHelper(Context c){

        this.c = c;
    }

    public String[] getTableHeader(){

        return tableHeader;
    }

    public String[][] getBloodValue(int userId){

        ArrayList<BloodValue> bloodValues = new DiabetesMemoDataSource(c).getAllBloodValue(userId);
        BloodValue b;
        bloodValueArr = new String[bloodValues.size()][3];

        for(int i = 0; i < bloodValues.size(); i++){

            b = bloodValues.get(i);

            Long date_milSec = b.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(date_milSec);

            bloodValueArr[i][0] = sdf.format(resultdate);
            bloodValueArr[i][1] = Integer.toString(b.getBlood_sugar());
            bloodValueArr[i][2] = b.getFeeling();

        }
        return bloodValueArr;
    }
}
