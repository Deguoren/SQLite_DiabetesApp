package com.example.maanjo.sqlite_diabetesapp.Database;

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
    public static final String COLUMN_time = "date";
    public static final String COLUMN_blood_sugar = "bloodSugar";


    public static final String sql_createUser_Table =
            "CREATE TABLE " + DIABETES_TABLE_user +
                    "(" + COLUMN_User_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_User_Name + " TEXT NOT NULL, " +
                    COLUMN_User_Password + " TEXT NOT NULL);";

    public static final String sql_createMetric_Table = "CREATE TABLE "
            + DIABETES_TABLE_metric + "("
            + COLUMN_feeling + " TEXT NOT NULL, "
            + COLUMN_blood_sugar + " INTEGER NOT NULL, "
            + COLUMN_time + " LONG NOT NULL, "
            + COLUMN_User_ID + " INTEGER, "
            + " FOREIGN KEY ("+COLUMN_User_ID+") REFERENCES "+ DIABETES_TABLE_user +"("+COLUMN_User_ID+"));";

    public DiabetesMemoDbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.d(LOG_TAG, "Die Tabelle  DIABETES_TABLE_user wird mit SQL-Befehl: " + sql_createUser_Table + " angelegt.");
            db.execSQL(sql_createUser_Table);

            Log.d(LOG_TAG, "Die Tabelle  DIABETES_TABLE_metric wird mit SQL-Befehl: " + sql_createMetric_Table + " angelegt.");
            db.execSQL(sql_createMetric_Table);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}