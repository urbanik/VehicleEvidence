package com.urbanik.entity;

import com.urbanik.mapper.BaseMapper;
import com.urbanik.mapper.VehicleMapper;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;

public class Vehicle implements Record {

    private String evidenceNumber;
    private String VIN;
    private int numberOfWheelAxles;
    private int operatingWeight;
    private boolean wanted;
    private Date STKValidityUntil;
    private Date EKValidityUntil;
    private BaseMapper mapper;


    public Vehicle(String evidenceNumber, String VIN, int numberOfWheelAxles, int operatingWeight, boolean wanted, Date STKValidityUntil, Date EKValidityUntil) {
        super();
        this.evidenceNumber = evidenceNumber;
        this.VIN = VIN;
        this.numberOfWheelAxles = numberOfWheelAxles;
        this.operatingWeight = operatingWeight;
        this.wanted = wanted;
        this.STKValidityUntil = STKValidityUntil;
        this.EKValidityUntil = EKValidityUntil;
        this.mapper = new VehicleMapper();
    }

    public Vehicle() {
        super();
        this.evidenceNumber = "zzzzzzz";
        this.VIN = "zzzzzzzzzzzzz";
        this.numberOfWheelAxles = 0;
        this.operatingWeight = 0;
        this.wanted = false;
        this.STKValidityUntil = new Date(1995 - 1900, 3, 16);
        this.EKValidityUntil = new Date(1995 - 1900, 3, 16);
        this.mapper = new VehicleMapper();
    }

    public String getEvidenceNumber() {
        return evidenceNumber;
    }

    public String getVIN() {
        return VIN;
    }

    public int getNumberOfWheelAxles() {
        return numberOfWheelAxles;
    }

    public int getOperatingWeight() {
        return operatingWeight;
    }

    public boolean isWanted() {
        return wanted;
    }

    public void setWanted(boolean wanted) {
        this.wanted = wanted;
    }

    public Date getSTKValidityUntil() {
        return STKValidityUntil;
    }

    public void setSTKValidityUntil(Date STKValidityUntil) {
        this.STKValidityUntil = STKValidityUntil;
    }

    public Date getEKValidityUntil() {
        return EKValidityUntil;
    }

    public void setEKValidityUntil(Date EKValidityUntil) {
        this.EKValidityUntil = EKValidityUntil;
    }

    public byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeBytes(this.evidenceNumber);
            dos.writeBytes(this.VIN);
            dos.writeInt(this.numberOfWheelAxles);
            dos.writeInt(this.operatingWeight);
            dos.writeBoolean(this.wanted);
            dos.writeLong(STKValidityUntil.getTime());
            dos.writeLong(EKValidityUntil.getTime());

        } catch (Exception e) {
            System.out.println("Error at driver license serialization!");
        }

        return baos.toByteArray();
    }

    public Vehicle fromBytes(byte[] byteArray) {
        try {
            byte[] en = new byte[7];
            byte[] vin = new byte[13];
            byte[] axles = new byte[4];
            byte[] weight = new byte[4];
            byte[] want = new byte[1];
            byte[] tk = new byte[8];
            byte[] ek = new byte[8];

            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            DataInputStream dis = new DataInputStream(bais);

            dis.read(en);
            dis.read(vin);
            dis.read(axles);
            dis.read(weight);
            dis.read(want);
            dis.read(tk);
            dis.read(ek);

            this.evidenceNumber = new String(en);
            this.VIN = new String(vin);
            this.numberOfWheelAxles = ByteBuffer.wrap(axles).getInt();
            this.operatingWeight = ByteBuffer.wrap(weight).getInt();
            this.wanted = want[0] == 0 ? false : true;
            this.STKValidityUntil = new Date(ByteBuffer.wrap(tk).getLong());
            this.EKValidityUntil = new Date(ByteBuffer.wrap(ek).getLong());
            return this;

        } catch (IOException ex) {
            System.out.println("Error at driver license deserialization!");
        }
        return null;
    }

    public int getSize() {
        return 45;
    }

    public Record newInstance() {
        return new Vehicle();
    }

    public BaseMapper getMapper() {
        return this.mapper;
    }

    public void setEvidenceNumber(String evidenceNumber) {
        this.evidenceNumber = evidenceNumber;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public void setNumberOfWheelAxles(int numberOfWheelAxles) {
        this.numberOfWheelAxles = numberOfWheelAxles;
    }

    public void setOperatingWeight(int operatingWeight) {
        this.operatingWeight = operatingWeight;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
