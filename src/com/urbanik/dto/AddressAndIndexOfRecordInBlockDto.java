package com.urbanik.dto;

import com.urbanik.entity.Record;
import com.urbanik.mapper.BaseMapper;

import java.io.*;
import java.nio.ByteBuffer;

public class AddressAndIndexOfRecordInBlockDto extends RecordDto {

    private int address;
    private int index;

    public AddressAndIndexOfRecordInBlockDto(int address, int index) {
        this.address = address;
        this.index = index;
    }

    public AddressAndIndexOfRecordInBlockDto() {
        this.address = 0;
        this.index = 0;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Address=" + address +
                ", index=" + index;
    }
}
