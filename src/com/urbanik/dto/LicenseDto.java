package com.urbanik.dto;

import java.util.Calendar;
import java.util.Date;

public class LicenseDto extends RecordDto {

    private String driverName;
    private int nameLength = 35;
    private String driverSurname;
    private int surnameLength = 35;
    private int licenseNumber;
    private Date licenseValidityUntil;
    private boolean prohibited;
    private int numberOfViolationsInLast12Months;

    public LicenseDto(String driverName, String driverSurname, int licenseNumber, Date licenseValidityUntil, boolean prohibited, int numberOfViolationsInLast12Months) {
        super();
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.licenseNumber = licenseNumber;
        this.licenseValidityUntil = licenseValidityUntil;
        this.prohibited = prohibited;
        this.numberOfViolationsInLast12Months = numberOfViolationsInLast12Months;
    }

    public LicenseDto(int licenseNumber) {
        super();
        this.driverName = "123456789012345678901234567890123456";
        this.driverSurname = "123456789012345678901234567890123456";
        this.licenseNumber = licenseNumber;
        this.licenseValidityUntil = new Date(1995, 3, 16);;
        this.prohibited = false;
        this.numberOfViolationsInLast12Months = 0;
    }

    public LicenseDto() {
        super();
        this.driverName = "123456789012345678901234567890123456";
        this.driverSurname = "123456789012345678901234567890123456";
        this.licenseNumber = Integer.MAX_VALUE;
        this.licenseValidityUntil = new Date(1995, 3, 16);
        this.prohibited = false;
        this.numberOfViolationsInLast12Months = 0;
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

    public int getSize() {
        return 99;
    }

    @Override
    public String toString() {
        return "License number=" + licenseNumber +
                ", valid until=" + licenseValidityUntil +
                ", prohibited=" + prohibited +
                ", n vliolations=" + numberOfViolationsInLast12Months +
                ", name=" + driverName + '\'' +
                ", surname='" + driverSurname + '\'';


    }

    @Override
    public boolean isValid() {
        if(licenseNumber == Integer.MAX_VALUE){
            return false;
        } else {
            return true;
        }
    }
}
