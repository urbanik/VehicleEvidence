package com.urbanik.entity;

import com.urbanik.mapper.BaseMapper;
import com.urbanik.mapper.VehicleENVHintMapper;

import java.io.*;

public class VehicleENHint implements Record{

    private AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock;
    private String evidenceNumber;
    private BaseMapper mapper;

    public VehicleENHint(AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock, String evidenceNumber) {
        this.addressAndIndexOfRecordInBlock = addressAndIndexOfRecordInBlock;
        this.evidenceNumber = evidenceNumber;
        mapper = new VehicleENVHintMapper();
    }

    public VehicleENHint(String evidenceNumber) {
        this.addressAndIndexOfRecordInBlock = new AddressAndIndexOfRecordInBlock();
        this.evidenceNumber = evidenceNumber;
        mapper = new VehicleENVHintMapper();
    }

    public VehicleENHint() {
        this.addressAndIndexOfRecordInBlock = new AddressAndIndexOfRecordInBlock();
        this.evidenceNumber = "zzzzzzz";
        mapper = new VehicleENVHintMapper();
    }

    public AddressAndIndexOfRecordInBlock getAddress() {
        return addressAndIndexOfRecordInBlock;
    }

    public void setAddress(AddressAndIndexOfRecordInBlock address) {
        this.addressAndIndexOfRecordInBlock = address;
    }

    public String getEvidenceNumber() {
        return evidenceNumber;
    }

    public void setEvidenceNumber(String evidenceNumber) {
        this.evidenceNumber = evidenceNumber;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.write(this.addressAndIndexOfRecordInBlock.getBytes());
            dos.writeBytes(this.evidenceNumber);
        } catch (Exception e) {
            System.out.println("Error at driver license serialization!");
        }
        return baos.toByteArray();
    }

    @Override
    public Object fromBytes(byte[] byteArray) {
        try {
            byte[] en = new byte[7];
            byte[] add = new byte[8];

            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            DataInputStream dis = new DataInputStream(bais);

            dis.read(add);
            dis.read(en);

            this.evidenceNumber = new String(en);
            this.addressAndIndexOfRecordInBlock = (AddressAndIndexOfRecordInBlock) addressAndIndexOfRecordInBlock.fromBytes(add);
            return this;

        } catch (IOException ex) {
            System.out.println("Error at driver license deserialization!");
        }
        return null;
    }

    @Override
    public int getSize() {
        return 15;
    }

    @Override
    public Object newInstance() {
        return new VehicleENHint();
    }

    @Override
    public BaseMapper getMapper() {
        return this.mapper;
    }

    @Override
    public int compareTo(Object o) {
        VehicleENHint vehicleENHint = (VehicleENHint) o;
        int cmp = this.getEvidenceNumber().compareTo(vehicleENHint.getEvidenceNumber());

        if(cmp < 0){

            return -1;

        }else if(cmp == 0){

            return 0;
        } else{

            return 1;
        }
    }
}
