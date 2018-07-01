package com.example.maanjo.sqlite_diabetesapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Beinhaltet alle Methoden, die direkt auf die Datenbank zugreifen (Werte eintragen & Auslesen)
 */

public class DiabetesMemoDataSource {

    private static final String LOG_TAG = DiabetesMemoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DiabetesMemoDbHelper dbHelper;

    /**
     * Konstruktor
     * Initialisiert den DiabetesMemoDbHelper (SQLiteOpenHelper)
     *
     * @param context: Anwendungskontext
     */
    public DiabetesMemoDataSource (Context context) {
        Log.d(LOG_TAG, "DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new DiabetesMemoDbHelper(context);
    }

    /**
     * Öffnet Verbindung zur Datenbank
     */
    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    /**
     * Schließt bestehende Verbindung zur Datenbank
     */
    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    /**
     * Hinzufügen eines User-Eintrags in der User-Tabelle
     *
     * @param name: Benutzername
     * @param password: Passwort des Nutzers
     */
    public void createUser(String name, String password){

        ContentValues userEntry = new ContentValues();

        userEntry.put(DiabetesMemoDbHelper.COLUMN_User_Name, name);
        userEntry.put(DiabetesMemoDbHelper.COLUMN_User_Password, password);

        database.insert(DiabetesMemoDbHelper.DIABETES_TABLE_user, null, userEntry);

        Log.d(LOG_TAG, name+ " wurde angelegt.");
    }

    /**
     * Hinzufügen eines Blutwert-Eintrags in der Metrics-Tabelle
     * Zeit wird nicht übergeben, da diese automatisch durch System.currentTimeMillis() ermittelt wird.
     *
     * @param bloodSugar: aktueller Blutzuckerwert
     * @param feeling: Stimmung des Nutzers
     * @param userId: automatisch vergebene UserID
     */
    public void createBloodValue(int bloodSugar, String feeling, int userId){

        ContentValues bloodEntry = new ContentValues();
        long time = System.currentTimeMillis();

        bloodEntry.put(DiabetesMemoDbHelper.COLUMN_blood_sugar, bloodSugar);
        bloodEntry.put(DiabetesMemoDbHelper.COLUMN_feeling, feeling);
        bloodEntry.put(DiabetesMemoDbHelper.COLUMN_time, time);
        bloodEntry.put(DiabetesMemoDbHelper.COLUMN_User_ID, userId);

        database.insert(DiabetesMemoDbHelper.DIABETES_TABLE_metric, null, bloodEntry);

        Log.d(LOG_TAG, "Blutzuckermesswert: " + bloodSugar +"|"+feeling+"|"+ "|"+ time+"|"+userId+" wurde angelegt");

    }

    /**
     * Überprüfen, ob Nutzername in der Datenbank vorhanden ist
     *
     * @param name: Eingegebener Nutzername
     * @return True oder False, abhängig davon, ob der User bereits vorhanden ist
     */
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

    /**
     * Überprüfen, ob das Password in der Datenbank vorhanden ist
     *
     * @param password: Eingegebenes Passwort
     * @return True oder Flase, abhängig davon, ob der User bereits vorhanden ist
     */
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

    /**
     * Eintrag der Tabelle Metrics an der Stelle des Cursors wird ermittelt
     *
     * @param cursor: Auswahl der SQL-Query
     * @return Blutzuckerwert; enthält die Informationen Gefühl, Zeit, Blutzuckerwert und UserID
     */
    private BloodValue cursorToBloodValue(Cursor cursor){

        int idBloodSugar = cursor.getColumnIndex(DiabetesMemoDbHelper.COLUMN_blood_sugar);
        int idFeeling = cursor.getColumnIndex(DiabetesMemoDbHelper.COLUMN_feeling);
        int idDate = cursor.getColumnIndex(DiabetesMemoDbHelper.COLUMN_time);
        int idUserId = cursor.getColumnIndex(DiabetesMemoDbHelper.COLUMN_User_ID);

        int bloodSugar = cursor.getInt(idBloodSugar);
        String feeling = cursor.getString(idFeeling);
        long time = cursor.getLong(idDate);
        int userId = cursor.getInt(idUserId);


       BloodValue bloodValue = new BloodValue(feeling, time, bloodSugar, userId);

        return bloodValue;
    }

    /**
     * Ausgabe aller Einträge der Tabelle Metrics mit der übergebenen UserID
     *
     * @param userId: Übergebene UserID
     * @return Alle Tupel der Relation Metrics in Form einer ArrayList
     */
    public ArrayList<BloodValue> getAllBloodValue(int userId) {

        database = dbHelper.getWritableDatabase();
        ArrayList<BloodValue> bloodValueList = new ArrayList<>();

        String[] columns = {DiabetesMemoDbHelper.COLUMN_User_ID, DiabetesMemoDbHelper.COLUMN_time,
                DiabetesMemoDbHelper.COLUMN_blood_sugar, DiabetesMemoDbHelper.COLUMN_feeling};

        String where = DiabetesMemoDbHelper.COLUMN_User_ID + " = " + userId;

        Cursor cursor = database.query(DiabetesMemoDbHelper.DIABETES_TABLE_metric,
                columns, where, null, null, null, null);

        cursor.moveToFirst();
        BloodValue value;

        if (cursor != null && cursor.moveToFirst()) {
            do {

                value = cursorToBloodValue(cursor);
                bloodValueList.add(value);

            } while (cursor.moveToNext());
        }
        Log.d(LOG_TAG, "Blutzuckerliste wurde erstellt");
        cursor.close();
        return bloodValueList;
    }

    /**
     * Ermittelt die UserID, die zu dem übergebenen UserName gehört
     *
     * @param userName: Übergebener Benutzername
     * @return User ID
     */
    public int getUserId(String userName){

        String[] columns = {DiabetesMemoDbHelper.COLUMN_User_ID};
        String where = DiabetesMemoDbHelper.COLUMN_User_Name +" LIKE '%"+userName+"%'";
        int userId;

        Cursor cursor = database.query(DiabetesMemoDbHelper.DIABETES_TABLE_user,
                columns, where, null, null,null, null);

        cursor.moveToFirst();

        if (cursor != null && cursor.moveToFirst()) {

            if (cursor.getCount() > 0) {

                Log.d(LOG_TAG, "UserId: " + cursor.getInt(0));
                userId = cursor.getInt(0);
                return userId;
            }
        }
        return cursor.getInt(0);
    }

}