package com.example.maanjo.sqlite_diabetesapp.Database;

public class BloodValue {

    private String feeling;
    private long time;
    private int blood_sugar;
    private int user_id;

    public BloodValue(String feeling, Long time, int blood_sugar, int user_id) {
        this.feeling = feeling;
        this.time = time;
        this.blood_sugar = blood_sugar;
        this.user_id = user_id;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getBlood_sugar() {
        return blood_sugar;
    }

    public void setBlood_sugar(int blood_sugar) {
        this.blood_sugar = blood_sugar;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String toString() {
        String output = blood_sugar + " x " + feeling + " x " + user_id;

        return output;
    }
}