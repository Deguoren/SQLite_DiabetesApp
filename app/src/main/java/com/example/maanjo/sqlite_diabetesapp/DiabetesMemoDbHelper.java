package com.example.maanjo.sqlite_diabetesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiabetesMemoDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = DiabetesMemoDbHelper.class.getSimpleName();

    public static final String DB_NAME = "Diabetes_Db";
    public static final int DB_VERSION = 1;

    public static final String DIABETES_TABLE_user = "userTable";

    public static final String DIABETES_TABLE_metric = "metric";

    public static final String COLUMN_User_ID = "id";
    public static final String COLUMN_User_Name = "userName";
    public static final String COLUMN_User_Password = "userPassword";

    public static final String COLUMN_feeling = "feeling";
    public static final String COLUMN_time = "time";
    public static final String COLUMN_blood_sugar = "bloodSugar";


    public static final String SQL_CREATE =
            "CREATE TABLE " + DIABETES_TABLE_user +
                    "(" + COLUMN_User_ID + " INTEGER PRIMARY KEY AUTOINCREMENT; " +
                    COLUMN_User_Name + " TEXT NOT NULL, " +
                    COLUMN_User_Password + " TEXT NOT NULL);" +

                    "CREATE TABLE " + DIABETES_TABLE_metric +
                    "(" + COLUMN_User_ID + " INTEGER FOREIGN KEY AUTOINCREMENT, " +
                    COLUMN_feeling + " TEXT NOT NULL, " +
                    COLUMN_blood_sugar + " INTEGER NOT NULL, " +
                    COLUMN_time + " TEXT NOT NULL);";


    public DiabetesMemoDbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}