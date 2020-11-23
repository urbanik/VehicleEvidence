package com.urbanik.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;


public class Block<R extends Record<R>> {

    private int size;
    private int m;
    private int recordSize;
    private int numberOfRecords;
    private Record[] records;
    private boolean[] isRecordValid;
    private int[] referencesAddresses;
    private R typeRecord;
    private int address;
    private int fatherAddress;
    private int additionalSpace;
    private int spaceLeft;
    private static final int DEFAULT_CLUSTER_SIZE = 4096;
    private int clusterSize;

    public Block(R typeRecord, int clusterSize) {
        this.clusterSize = clusterSize;
        this.size = clusterSize; // defaultna velkost clustra pri citani
        this.typeRecord = typeRecord;
        this.recordSize = typeRecord.getSize();

        int potentialNumberOfRecords = (int) Math.floor(this.size / this.recordSize);
        this.additionalSpace = (potentialNumberOfRecords + 1)*4 + potentialNumberOfRecords + 4 + 4; // miesto pre adresy potomkov a adresu + adresu otca
        this.numberOfRecords = (int) Math.floor((this.size - additionalSpace)/this.recordSize);
        this.m = numberOfRecords + 1;
        this.spaceLeft = clusterSize - additionalSpace - this.numberOfRecords*this.recordSize;

        //this.size = numberOfRecords*recordSize + m*4 + numberOfRecords + 4 + 4; // + numberOfRecords kvoli booleanu ci je rekord validny alebo nie + referencie(adresy) + adresa + adresa otca
        this.records = new Record[this.numberOfRecords];
        this.isRecordValid = new boolean[this.numberOfRecords];
        this.referencesAddresses = new int[m];
        for(int i = 0; i < m; i++){
            this.referencesAddresses[i] = -1;
        }
        this.fatherAddress = -1;
        this.address = 0;
        initializeBlock();
    }

    public Record findRecord(R record){

        for (Record r : this.records) {
            if(record.compareTo(r) == 0) return r;
        }
        return null;
    }

    public int getRecordIndex(R record){
        int index = 0;
        for (int i = 0; i < getRecords().length; i++) {
            if(record.compareTo(getRecords()[i]) == 0) index = i;
        }
        return index;
    }

    public int findCoveringBlock(R record){

        int address = -1;

        // Mensie
        Record lesser = this.records[0];
        if (record.compareTo(lesser) <= 0) {
            address = this.referencesAddresses[0];
        }

        // Vacsie
        if(this.getNumberOfValidRecords() != 0){
            Record last = this.records[this.getNumberOfValidRecords() - 1];
            if (record.compareTo(last) > 0) {
                address = this.referencesAddresses[this.getNumberOfValidRecords()];
            }
        }

        for (int i = 1; i < this.getNumberOfValidRecords(); i++) { // vnutorne
            Record prev = this.records[i - 1];
            Record next = this.records[i];
            if (record.compareTo(prev) > 0 && record.compareTo(next) < 0) {
                address = this.referencesAddresses[i];
                break;
            }
        }
        return address;
    }

    public void insert(R record){
        int index = findEmptySpot();
        this.records[index] = record;
        if(index != getNumberOfRecords()){
            this.isRecordValid[index] = true;
        }
    }

    public int insertAndReturnIndex(R record){
        int index = findEmptySpot();
        this.records[index] = record;
        if(index != getNumberOfRecords()){
            this.isRecordValid[index] = true;
        }
        return index;
    }

    public int findEmptySpot(){
        int index = 0;
        for(int i = 0; i < this.numberOfRecords; i++){
            if(!this.isRecordValid[i]) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int getNumberOfValidRecords(){
        int count = 0;

        for (boolean b : this.isRecordValid) {
            if(b == true) count++;
        }

        return count;
    }

    public boolean isRoot(){
        int count = 0;

        for (int address : this.referencesAddresses) {
            if(address == 0) count++;
        }

        if(count == 0){
            return true;
        } else {
            return false;
        }
    }

    private void initializeBlock() {
        for(int i = 0; i < numberOfRecords; i++){
            this.records[i] = typeRecord;
        }
    }

    public Record[] getRecords() {
        return records;
    }

    public boolean[] getIsRecordValid() {
        return isRecordValid;
    }

    public void setIsRecordValid(boolean[] isRecordValid) {
        this.isRecordValid = isRecordValid;
    }

    public byte[] getBytes() throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        for(int i = 0; i < this.records.length; i++){
            if(this.isRecordValid[i] == true){
                dos.write(this.records[i].getBytes());
            } else {
                dos.write(typeRecord.getBytes());
            }
        }

        for(int i = 0; i < this.isRecordValid.length; i++){

            if(this.isRecordValid[i]){
                dos.writeByte(1);
            } else {
                dos.writeByte(0);
            }
        }

        for(int i = 0; i < this.numberOfRecords + 1; i++){

            dos.writeInt(this.referencesAddresses[i]);
        }
        dos.writeInt(this.address);
        dos.writeInt(this.fatherAddress);
        return baos.toByteArray();
    }

    public void fromBytes(byte[] byteArray) {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);

        try {
        for (int i = 0; i < this.records.length; i++) {

            R record = (R) typeRecord.newInstance(); // records[i];
            byte[] array = new byte[recordSize];
            inputStream.read(array);
            records[i] = record.fromBytes(array);
        }

        byte[] array2 = new byte[this.numberOfRecords];
        inputStream.read(array2);
        for (int i = 0; i < this.isRecordValid.length; i++) {
            this.isRecordValid[i] = array2[i] == 0 ? false : true;
        }

        for (int i = 0; i < this.referencesAddresses.length; i++) {
            byte[] array3 = new byte[4];
            inputStream.read(array3);
            this.referencesAddresses[i] = ByteBuffer.wrap(array3).getInt();
        }

        byte[] array4 = new byte[4];
        inputStream.read(array4);
        this.address = ByteBuffer.wrap(array4).getInt();
        byte[] array5 = new byte[4];
        inputStream.read(array5);
        this.fatherAddress = ByteBuffer.wrap(array5).getInt();
        inputStream.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public int getSize() {
         return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public void setRecords(Record[] records) {
        this.records = records;
    }

    public Record getTypeRecord() {
        return typeRecord;
    }

    public void setTypeRecord(R typeRecord) {
        this.typeRecord = typeRecord;
    }

    public int[] getReferencesAddresses() {
        return referencesAddresses;
    }

    public void setReferencesAddresses(int[] referencesAddresses) {
        this.referencesAddresses = referencesAddresses;
    }

    public int getFatherAddress() {
        return fatherAddress;
    }

    public void setFatherAddress(int fatherAddress) {
        this.fatherAddress = fatherAddress;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void moveReferences(int index) {

        int[] array = new int[this.referencesAddresses.length];
        for(int i = 0; i < this.records.length; i++ ){
            if(i < index) {
            array[i] = this.referencesAddresses[i];
            } else {
                if(i + 2 < this.referencesAddresses.length){
                    array[i + 2] = this.referencesAddresses[i + 1];
                }
            }
        }
        this.referencesAddresses = array;
    }

    public void updateRecord(R record) {
        for (int i = 0; i < getNumberOfValidRecords(); i++) {
            if(this.records[i].compareTo(record) == 0){
                this.records[i] = record;
            }
        }
    }

    public void updateRecordOnIndex(R record, int index) {
        this.records[index] = record;
    }
}
