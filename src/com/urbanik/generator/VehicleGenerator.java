package com.urbanik.generator;

import com.urbanik.entity.License;
import com.urbanik.entity.Vehicle;

import java.util.ArrayList;
import java.util.Date;

public class VehicleGenerator extends MyGenerator {

    ArrayList<String> ens;
    ArrayList<String> vins;

    public VehicleGenerator(int n) {
        super(n);
        this.ens = new ArrayList<>();
        this.vins = new ArrayList<>();

        while (ens.size() < getN()) {
            String data = createCode(7);

            if (!ens.contains(data)) {
                ens.add(data);
            }
        }

        while (vins.size() < getN()) {
            String data = createCode(13);

            if (!vins.contains(data)) {
                vins.add(data);
            }
        }

        //int limit = (ens.size() > vins.size()) ? vins.size() : ens.size();

        for(int i = 0; i < ens.size(); i++){
            Vehicle vehicle = new Vehicle(ens.get(i), vins.get(i), getRandomInt(1, 6), getRandomInt(2, 30), false, new Date(getRandomInt(2019, 2030) - 1900, getRandomInt(1, 12), getRandomInt(1, 28)), new Date(getRandomInt(2019, 2030) - 1900, getRandomInt(1, 12), getRandomInt(1, 28)));
            getRecords()[i] = vehicle;
        }
    }
}
