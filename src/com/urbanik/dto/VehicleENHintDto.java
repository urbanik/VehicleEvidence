package com.urbanik.dto;

import com.urbanik.entity.AddressAndIndexOfRecordInBlock;
import com.urbanik.entity.Record;
import com.urbanik.mapper.BaseMapper;

import java.io.*;

public class VehicleENHintDto extends RecordDto {

    private AddressAndIndexOfRecordInBlockDto addressAndIndexOfRecordInBlock;
    private String evidenceNumber;

    public VehicleENHintDto(AddressAndIndexOfRecordInBlockDto addressAndIndexOfRecordInBlock, String evidenceNumber) {
        this.addressAndIndexOfRecordInBlock = addressAndIndexOfRecordInBlock;
        this.evidenceNumber = evidenceNumber;
    }

    public VehicleENHintDto(String evidenceNumber) {
        this.addressAndIndexOfRecordInBlock = new AddressAndIndexOfRecordInBlockDto();
        this.evidenceNumber = evidenceNumber;
    }

    public VehicleENHintDto() {
        this.addressAndIndexOfRecordInBlock = new AddressAndIndexOfRecordInBlockDto();
        this.evidenceNumber = "zzzzzzz";
    }

    public AddressAndIndexOfRecordInBlockDto getAddress() {
        return addressAndIndexOfRecordInBlock;
    }

    public void setAddress(AddressAndIndexOfRecordInBlockDto address) {
        this.addressAndIndexOfRecordInBlock = address;
    }

    public String getEvidenceNumber() {
        return evidenceNumber;
    }

    public void setEvidenceNumber(String evidenceNumber) {
        this.evidenceNumber = evidenceNumber;
    }

    public BaseMapper getMapper() {
        return null;
    }

    @Override
    public String toString() {
        return addressAndIndexOfRecordInBlock +
                ", evidenceNumber='" + evidenceNumber;
    }

    @Override
    public boolean isValid() {
        if(!this.evidenceNumber.equals("zzzzzzz")){
            return true;
        } else {
            return false;
        }
    }
}
