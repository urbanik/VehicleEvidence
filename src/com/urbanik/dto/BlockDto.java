package com.urbanik.dto;

public class BlockDto {

    private int size;
    private int recordSize;
    private int numberOfRecords;
    private RecordDto[] records;
    private RecordDto record;
    private int address;
    private int fatherAddress;


    public BlockDto(int m, RecordDto recordDto) {

        initializeBlock();

        this.numberOfRecords = m-1;
        this.size = numberOfRecords*recordSize;
        this.records = new RecordDto[this.numberOfRecords];
        this.address = 0;
        this.fatherAddress = 0;
        initializeBlock();

    }

    private void initializeBlock() {
        for(int i = 0; i < numberOfRecords; i++){
            this.records[i] = record;
        }
    }

    public RecordDto[] getRecords() {
        return records;
    }

    public int getSize(){
        return 0;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getFatherAddress() {
        return fatherAddress;
    }

    public void setFatherAddress(int fatherAddress) {
        this.fatherAddress = fatherAddress;
    }
}
