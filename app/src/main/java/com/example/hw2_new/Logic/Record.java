package com.example.hw2_new.Logic;

import androidx.annotation.NonNull;

import com.google.android.material.imageview.ShapeableImageView;

public class Record implements Comparable<Record> {
    private long date ;
    private String titleScore ="SCORE";
    private String score = "";
    private ShapeableImageView img ;
    private double latitude ;
    private double longitude ;


    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public String getTitleScore(){
        return titleScore;
    }

    public ShapeableImageView getImg(){
        return img;
    }
    public long getDate() {
        return date;
    }
    public String getScore() {
        return score;
    }

    public Record setDate(long date) {
        this.date = date;
        return this;
    }

    public Record setTitleScore(String titleScore) {
        this.titleScore = titleScore;
        return this;
    }

    public Record setScore(String score) {
        this.score = score;
        return this;
    }

    public Record setImg(ShapeableImageView img) {
        this.img = img;
        return this;
    }

    public Record setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Record setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String toString(){
        return "Records{" +
                "title='" + titleScore + '\'' +
                ", score='" + score + '\'' +
                ", image='" + img + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public int compareTo(@NonNull Record record){
        Integer score1 = Integer.parseInt(this.score);
        Integer score2 = Integer.parseInt(record.score);
        return score2.compareTo(score1);
    }

}
