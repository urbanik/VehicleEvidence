package com.urbanik.structure;

import com.urbanik.entity.AddressAndIndexOfRecordInBlock;
import com.urbanik.entity.Block;
import com.urbanik.entity.Record;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class HeapFile<R> {

    String fileName;
    Record recordType;
    RandomAccessFile raf;
    int numberOfBlocks;
    Block tmp;
    int clusterSize;

    public HeapFile(String fileName, Record recordType, int clusterSize) {
        this.clusterSize = clusterSize;
        this.tmp = new Block(recordType, clusterSize);
        this.tmp.setAddress(0);
        this.recordType = recordType;
        this.fileName = fileName;
        this.numberOfBlocks = 1;

        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.seek(0);
            raf.write(tmp.getBytes());
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HeapFile() {
    }

    public R find(AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock) {

        byte[] blockBytes = new byte[this.tmp.getSize()];
        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.seek(addressAndIndexOfRecordInBlock.getAddress());
            raf.read(blockBytes); // nacitam blok
            raf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tmp.fromBytes(blockBytes); // deserializujem blok
        return (R) this.tmp.getRecords()[addressAndIndexOfRecordInBlock.getIndex()];

    }

    public AddressAndIndexOfRecordInBlock insert(R record) {

        readBlock(this.tmp.getAddress());

        if(this.tmp.getNumberOfValidRecords() == this.tmp.getNumberOfRecords()){
            this.tmp = addBlock();
            this.tmp.setAddress(this.numberOfBlocks*this.tmp.getSize());
            this.numberOfBlocks++;
        }


        int index = this.tmp.insertAndReturnIndex((Record) record);
        writeBlock();

        return new AddressAndIndexOfRecordInBlock(this.tmp.getAddress(), index);
    }

    public R update(R record, AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock) {
        readBlock(addressAndIndexOfRecordInBlock.getAddress());
        this.tmp.updateRecordOnIndex((Record) record, addressAndIndexOfRecordInBlock.getIndex());
        writeBlock();
        return (R) this.tmp.getRecords()[addressAndIndexOfRecordInBlock.getIndex()];
    }

    public Block addBlock(){
        return new Block(recordType, clusterSize);
    }

    public void readBlock(int address){

        byte[] blockBytes = new byte[this.tmp.getSize()];
        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.seek(address);
            raf.read(blockBytes); // nacitam blok
            raf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tmp.fromBytes(blockBytes); // deserializujem blok
    }

    public void writeBlock(){

        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.seek(this.tmp.getAddress());
            raf.write(this.tmp.getBytes());// zapisem blok
            raf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Block> getBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        try {
            for(int i = 0; i < numberOfBlocks; i++){
                raf = new RandomAccessFile(this.fileName, "rw");
                byte[] block1bytes = new byte[this.tmp.getSize()];
                raf.seek(i*this.tmp.getSize());
                raf.read(block1bytes);
                Block block1 = new Block(recordType, clusterSize);
                block1.fromBytes(block1bytes);
                blocks.add(block1);
            }
            raf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blocks;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Record getRecordType() {
        return recordType;
    }

    public void setRecordType(Record recordType) {
        this.recordType = recordType;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public void setNumberOfBlocks(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }

    public Block getTmp() {
        return tmp;
    }

    public void setTmp(Block tmp) {
        this.tmp = tmp;
    }
}
