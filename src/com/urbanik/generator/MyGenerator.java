package com.urbanik.generator;

import com.urbanik.entity.License;
import com.urbanik.entity.Record;

import java.util.Random;

public class MyGenerator {

    private int n;
    private Record[] records;

    private final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private final String NUMBER = "0123456789";

    private final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    Random random;

    public MyGenerator(int n) {
        this.n = n;
        this.records = new Record[n];
        this.random = new Random();
    }

    public String createCode(int length) {

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = this.random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);

        }
        return sb.toString();
    }

    public int getRandomInt(int min, int max) {

        return this.random.nextInt((max - min) + 1) + min;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Record[] getRecords() {
        return records;
    }

    public void setRecords(Record[] records) {
        this.records = records;
    }
}
