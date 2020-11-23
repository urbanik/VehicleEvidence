package com.urbanik.dto;

import com.urbanik.entity.AddressAndIndexOfRecordInBlock;
import com.urbanik.entity.Record;
import com.urbanik.mapper.BaseMapper;

import java.io.*;

public class VehicleVINHintDto extends RecordDto {

    private AddressAndIndexOfRecordInBlockDto address;
    private String vin;

    public VehicleVINHintDto(AddressAndIndexOfRecordInBlockDto address, String vin) {
        this.address = address;
        this.vin = vin;
    }

    public VehicleVINHintDto(String vin) {
        this.address = new AddressAndIndexOfRecordInBlockDto();
        this.vin = vin;
    }

    public VehicleVINHintDto() {
        this.address = new AddressAndIndexOfRecordInBlockDto();
        this.vin = "zzzzzzzzzzzzz";
    }

    public AddressAndIndexOfRecordInBlockDto getAddress() {
        return address;
    }

    public void setAddress(AddressAndIndexOfRecordInBlockDto address) {
        this.address = address;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public BaseMapper getMapper() {
        return null;
    }

    @Override
    public String toString() {
        return address +
                ", vin='" + vin;
    }

    @Override
    public boolean isValid() {
        if(!this.vin.equals("zzzzzzzzzzzzz")){
            return true;
        } else {
            return false;
        }
    }
}
