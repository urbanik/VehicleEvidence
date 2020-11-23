package com.urbanik.entity;

import com.urbanik.mapper.BaseMapper;
import com.urbanik.mapper.LicenseMapper;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;

public class License implements Record{

    private String driverName;
    private int nameLength = 35;
    private int nameRealLength = 0;
    private String driverSurname;
    private int surnameLength = 35;
    private int surnameRealLength = 0;
    private int licenseNumber;
    private Date licenseValidityUntil;
    private boolean prohibited;
    private int numberOfViolationsInLast12Months;
    private BaseMapper mapper;

    public License(String driverName, String driverSurname, int licenseNumber, Date licenseValidityUntil, boolean prohibited, int numberOfViolationsInLast12Months) {
        super();
        this.driverName = driverName;
        this.nameRealLength = driverName.length();
        this.driverSurname = driverSurname;
        this.surnameRealLength = driverSurname.length();
        this.licenseNumber = licenseNumber;
        this.licenseValidityUntil = licenseValidityUntil;
        this.prohibited = prohibited;
        this.numberOfViolationsInLast12Months = numberOfViolationsInLast12Months;
        this.mapper = new LicenseMapper();
    }

    public License() {
        super();
        this.driverName = "";
        this.driverSurname = "";
        this.licenseNumber = Integer.MAX_VALUE;
        this.licenseValidityUntil = new Date(1995 - 1900, 3, 16);
        this.prohibited = false;
        this.numberOfViolationsInLast12Months = 0;
        this.mapper = new LicenseMapper();

    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public Date getLicenseValidityUntil() {
        return licenseValidityUntil;
    }

    public boolean isProhibited() {
        return prohibited;
    }

    public int getNumberOfViolationsInLast12Months() {
        return numberOfViolationsInLast12Months;
    }

    public void setLicenseValidityUntil(Date licenseValidityUntil) {
        this.licenseValidityUntil = licenseValidityUntil;
    }

    public void setProhibited(boolean prohibited) {
        this.prohibited = prohibited;
    }

    public void setNumberOfViolationsInLast12Months(int numberOfViolationsInLast12Months) {
        this.numberOfViolationsInLast12Months = numberOfViolationsInLast12Months;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getNameLength() {
        return nameLength;
    }

    public void setNameLength(int nameLength) {
        this.nameLength = nameLength;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public int getSurnameLength() {
        return surnameLength;
    }

    public void setSurnameLength(int surnameLength) {
        this.surnameLength = surnameLength;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String fullFillString(String string, int length) {

        int diff = length - string.length();
        String s = "";

        if (diff >= 0) {
            s = string;
            for (int i = 0; i < diff; i++) {
                s += "?";
            }
        } else if (diff < 0) {
            s = string.substring(0, 35);
        }
        return s;
    }

    @Override
    public byte[] getBytes() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeBytes(fullFillString(driverName, nameLength));
            dos.writeInt(nameRealLength);
            dos.writeBytes(fullFillString(driverSurname, surnameLength));
            dos.writeInt(surnameRealLength);
            dos.writeInt(licenseNumber);
            dos.writeLong(licenseValidityUntil.getTime());
            dos.writeBoolean(prohibited);
            dos.writeInt(numberOfViolationsInLast12Months);

        } catch (Exception e) {
            System.out.println("Error at driver license serialization!");
        }

        return baos.toByteArray();
    }

    @Override
    public License fromBytes(byte[] byteArray) {

        try {
            byte[] nameBlock = new byte[35];
            byte[] nameLength = new byte[4];

            byte[] surnameBlock = new byte[35];
            byte[] surnameLength = new byte[4];

            byte[] licenseNumber = new byte[4];
            byte[] licenseValidityUntil = new byte[8];
            byte[] prohibity = new byte[1];
            byte[] numberOfViolations = new byte[4];

            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            DataInputStream dis = new DataInputStream(bais);

            dis.read(nameBlock);
            dis.read(nameLength);
            dis.read(surnameBlock);
            dis.read(surnameLength);
            dis.read(licenseNumber);
            dis.read(licenseValidityUntil);
            dis.read(prohibity);
            dis.read(numberOfViolations);

            String nameDirty = new String(nameBlock);
            this.nameRealLength = ByteBuffer.wrap(nameLength).getInt();
            this.driverName = nameDirty.substring(0, nameRealLength);

            String surnameDirty = new String(surnameBlock);
            this.surnameRealLength = ByteBuffer.wrap(surnameLength).getInt();
            this.driverSurname = surnameDirty.substring(0, surnameRealLength);

            this.licenseNumber = ByteBuffer.wrap(licenseNumber).getInt();
            this.licenseValidityUntil = new Date(ByteBuffer.wrap(licenseValidityUntil).getLong());
            this.prohibited = prohibity[0] == 0 ? false : true;
            this.numberOfViolationsInLast12Months = ByteBuffer.wrap(numberOfViolations).getInt();
            return this;

        } catch (IOException ex) {
            System.out.println("Error at driver license deserialization!");
        }
        return null;
    }

    public int getSize() {
        return 95;
    }

    public Record newInstance() {
        return new License();
    }

    public BaseMapper getMapper() {
        return this.mapper;
    }

    @Override
    public int compareTo(Object o) {

        License license = (License) o;
        Integer priority1 = new Integer(this.licenseNumber);
        Integer priority2 = new Integer(license.getLicenseNumber());

        int cmp = priority1.compareTo(priority2);

        if(cmp < 0){

            return -1;

        }else if(cmp == 0){

            return 0;
        } else{

            return 1;
        }
    }

    @Override
    public String toString() {
        return   "licenseNumber=" + licenseNumber +
                ", licenseValidityUntil=" + licenseValidityUntil +
                ", driverName='" + driverName + '\'' +
                ", driverSurname='" + driverSurname + '\'' +
                ", prohibited=" + prohibited +
                ", numberOfViolationsInLast12Months=" + numberOfViolationsInLast12Months;
    }
}
