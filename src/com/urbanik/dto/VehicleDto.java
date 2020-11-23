package com.urbanik.dto;

import com.urbanik.entity.Record;

import java.util.Date;

public class VehicleDto extends RecordDto {

    private String evidenceNumber;
    private String VIN;
    private int numberOfWheelAxles;
    private int operatingWeight;
    private boolean wanted;
    private Date STKValidityUntil;
    private Date EKValidityUntil;

    public VehicleDto(String evidenceNumber, String VIN, int numberOfWheelAxles, int operatingWeight, boolean wanted, Date STKValidityUntil, Date EKValidityUntil) {
        super();
        this.evidenceNumber = evidenceNumber;
        this.VIN = VIN;
        this.numberOfWheelAxles = numberOfWheelAxles;
        this.operatingWeight = operatingWeight;
        this.wanted = wanted;
        this.STKValidityUntil = STKValidityUntil;
        this.EKValidityUntil = EKValidityUntil;
    }

    public VehicleDto() {
        super();
        this.evidenceNumber = "zzzzzzz";
        this.VIN = "zzzzzzzzzzzzz";
        this.numberOfWheelAxles = 0;
        this.operatingWeight = 0;
        this.wanted = false;
        this.STKValidityUntil = new Date(1995, 3, 16);
        this.EKValidityUntil = new Date(1995, 3, 16);
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
    public boolean isValid() {
        if(!this.evidenceNumber.equals("zzzzzzz")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "EvidenceNumber='" + evidenceNumber + '\'' +
                ", VIN='" + VIN + '\'' +
                ", numberOfWheelAxles=" + numberOfWheelAxles +
                ", operatingWeight=" + operatingWeight +
                ", wanted=" + wanted +
                ", STKValidityUntil=" + STKValidityUntil +
                ", EKValidityUntil=" + EKValidityUntil;
    }
}
