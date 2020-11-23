package com.urbanik.entity;

import com.urbanik.mapper.BaseMapper;
import com.urbanik.mapper.VehicleENVHintMapper;
import com.urbanik.mapper.VehicleVINHintMapper;

import java.io.*;

public class VehicleVINHint implements Record {

    private AddressAndIndexOfRecordInBlock address;
    private String vin;
    private BaseMapper mapper;

    public VehicleVINHint(AddressAndIndexOfRecordInBlock address, String vin) {
        this.address = address;
        this.vin = vin;
        mapper = new VehicleVINHintMapper();
    }

    public VehicleVINHint(String vin) {
        this.address = new AddressAndIndexOfRecordInBlock();
        this.vin = vin;
        mapper = new VehicleVINHintMapper();
    }

    public VehicleVINHint() {
        this.address = new AddressAndIndexOfRecordInBlock();
        this.vin = "zzzzzzzzzzzzz";
        mapper = new VehicleVINHintMapper();
    }

    public AddressAndIndexOfRecordInBlock getAddress() {
        return address;
    }

    public void setAddress(AddressAndIndexOfRecordInBlock address) {
        this.address = address;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.write(this.address.getBytes());
            dos.writeBytes(this.vin);
        } catch (Exception e) {
            System.out.println("Error at driver license serialization!");
        }
        return baos.toByteArray();
    }

    @Override
    public Object fromBytes(byte[] byteArray) {
        try {

            byte[] vin = new byte[13];
            byte[] add = new byte[8];

            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            DataInputStream dis = new DataInputStream(bais);

            dis.read(add);
            dis.read(vin);

            this.vin = new String(vin);
            this.address = (AddressAndIndexOfRecordInBlock) address.fromBytes(add);
            return this;

        } catch (IOException ex) {
            System.out.println("Error at driver license deserialization!");
        }
        return null;
    }

    @Override
    public int getSize() {
        return 21;
    }

    @Override
    public Object newInstance() {
        return new VehicleVINHint();
    }

    @Override
    public BaseMapper getMapper() {
        return this.mapper;
    }

    @Override
    public int compareTo(Object o) {
        VehicleVINHint vehicleVINHint = (VehicleVINHint) o;
        int cmp = this.getVin().compareTo(vehicleVINHint.getVin());

        if(cmp < 0){

            return -1;

        }else if(cmp == 0){

            return 0;
        } else{

            return 1;
        }
    }
}
