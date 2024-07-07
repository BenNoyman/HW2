package com.example.hw2_new.Logic;

import java.util.ArrayList;
import java.util.Collections;

public class TopTenRecords {

    public static int MAX_LIST =10;
    private String name;
    private ArrayList<Record> records = new ArrayList<>();

    public TopTenRecords(){
    }

    public String getName() {
        return name;
    }

    public TopTenRecords setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public TopTenRecords setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public void sortRecord(){
    Collections.sort(records);
    }

    public void addItem(String score , double latitude , double longitude){
        if (records.size() == MAX_LIST)
            records.remove(records.size() - 1);

        records.add(new Record()
                .setScore(score)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setTitleScore("SCORE")
        );
        sortRecord();
    }

    public String toString(){
        return "RecordsList{" +
                "name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}
