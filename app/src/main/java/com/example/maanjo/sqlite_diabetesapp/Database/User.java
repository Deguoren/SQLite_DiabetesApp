package com.example.maanjo.sqlite_diabetesapp.Database;

/**
 * Objektklasse zum Zwischenspeichern der ausgelesenen Datenbanktupel der Tabelle User.
 */
public class User {

    private String userName;
    private String userPw;

    /**
     * Konstruktor
     * Initialisieren der Instanzvariablen mithilfe der Übergabeparameter
     *
     * @param userName: Benutzername
     * @param userPw: Passwort
     */
    public User(String userName, String userPw) {

        this.userName = userName;
        this.userPw = userPw;
    }

    /**
     * Getter- & Setter-Methoden der Instanzvariablen, um auf diese zuzugreifen.
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
}
