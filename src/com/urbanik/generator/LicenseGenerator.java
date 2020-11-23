package com.urbanik.generator;

import com.urbanik.entity.License;

import java.util.ArrayList;
import java.util.Date;

public class LicenseGenerator extends MyGenerator {

    ArrayList<Integer> numbers;

    public LicenseGenerator(int n) {
        super(n);

        numbers = new ArrayList<>();

        while (numbers.size() < getN()) {
            int data = getRandomInt(1000000,9999999);

            if (!numbers.contains(data)) {
                numbers.add(data);
            }
        }

        for(int i = 0; i < numbers.size(); i++){
            getRecords()[i] = new License(createCode(getRandomInt(5, 35)), createCode(getRandomInt(5, 35)), numbers.get(i), new Date(getRandomInt(2019, 2030) - 1900, getRandomInt(1, 12), getRandomInt(1, 28)), false, getRandomInt(0, 10));
        }
    }
}
