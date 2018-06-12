package com.example.maanjo.sqlite_diabetesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DiabetesMemoDataSource {

    private static final String LOG_TAG = DiabetesMemoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DiabetesMemoDbHelper dbHelper;

    private String[] columns = {
            DiabetesMemoDbHelper.COLUMN_User_ID,
            DiabetesMemoDbHelper.COLUMN_User_Name,
            DiabetesMemoDbHelper.COLUMN_User_Password
    };


    public DiabetesMemoDataSource (Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new DiabetesMemoDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public void createUser(String name, String password){

        ContentValues userEntry = new ContentValues();

        userEntry.put(DiabetesMemoDbHelper.COLUMN_User_Name, name);
        userEntry.put(DiabetesMemoDbHelper.COLUMN_User_Password, password);

        database.insert(DiabetesMemoDbHelper.DIABETES_TABLE_user, null, userEntry);

        Log.d(LOG_TAG, name+ " wurde angelegt.");
    }

    public void createBloodValue(int bloodSugar, String feeling){

        ContentValues bloodEntry = new ContentValues();

        bloodEntry.put(DiabetesMemoDbHelper.COLUMN_blood_sugar, bloodSugar);
        bloodEntry.put(DiabetesMemoDbHelper.COLUMN_feeling, feeling);

        database.insert(DiabetesMemoDbHelper.DIABETES_TABLE_metric, null, bloodEntry);

        Log.d(LOG_TAG, "Blutzuckermesswert " + bloodSugar + "wurde angelegt");

    }

    public boolean checkUserName(String name){

        String[] columns = {DiabetesMemoDbHelper.COLUMN_User_Name};
        String where = DiabetesMemoDbHelper.COLUMN_User_Name + " = ?";
        String[] whereArgs = {name};

        Cursor cursor = database.query(DiabetesMemoDbHelper.DIABETES_TABLE_user,
                columns, where, whereArgs, null,null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }

    public boolean checkPassword(String password){

        String[] columns = {DiabetesMemoDbHelper.COLUMN_User_Password};
        String where = DiabetesMemoDbHelper.COLUMN_User_Password + " = ?";
        String[] whereArgs = {password};

        Cursor cursor = database.query(DiabetesMemoDbHelper.DIABETES_TABLE_user,
                columns, where, whereArgs, null,null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }

    private BloodValue cursorToBloodValue(Cursor cursor){

        int idBloodSugar = cursor.getColumnIndex(DiabetesMemoDbHelper.COLUMN_blood_sugar);
        int idFeeling = cursor.getColumnIndex(DiabetesMemoDbHelper.COLUMN_feeling);

        int bloodSugar = cursor.getInt(idBloodSugar);
        String feeling = cursor.getString(idFeeling);


        BloodValue bloodValue = new BloodValue()


    }

    public List<BloodValue> getAllBloodValue(){

        List<BloodValue> bloodValueList = new ArrayList<>();

        Cursor cursor = database.query(DiabetesMemoDbHelper.DIABETES_TABLE_metric,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        BloodValue value;

        while(!cursor.isAfterLast()){

            value = cursorToBloodValue(cursor);
            bloodValueList.add(value);
            Log.d(LOG_TAG, "Blutzucker: " + value.getBlood_sugar());
            cursor.moveToNext();

        }


    }

}