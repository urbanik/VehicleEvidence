package com.urbanik.structure;

import com.urbanik.entity.Block;
import com.urbanik.entity.Record;

import java.io.*;
import java.util.ArrayList;

public class BTree<R extends Record<R>> implements Structure<R> {

    String fileName;
    int rootAddress;
    Block tmp;
    int height;
    RandomAccessFile raf;
    Record recordType;
    int numberOfBlocks;
    int clusterSize;

    public BTree(Record recordType, String fileName, int clusterSize) {
        this.clusterSize = clusterSize;
        this.recordType = recordType;
        this.fileName = fileName;
        this.rootAddress = 0;
        this.tmp = new Block(recordType, clusterSize);
        this.height = 1;
        this.numberOfBlocks = 1;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.seek(rootAddress);
            raf.write(tmp.getBytes());
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean nRecordsCheck(Block b) {

        if (b.isRoot()) {

            if (b.getNumberOfValidRecords() > 1) {
                return true;
            } else {
                return false;
            }
        } else {
            if (((b.getNumberOfRecords() + 1) / 2) - 1 < b.getNumberOfValidRecords()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Block readBlock(int address){

        Block block = new Block(recordType, clusterSize);
        byte[] blockBytes = new byte[block.getSize()]; // uprava na block z tmp, lebo tmp cital 0 bajtov
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
        block.fromBytes(blockBytes); // deserializujem blok
        return block;
    }

    public void writeBlock(Block block){

        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.seek(block.getAddress());
            raf.write(block.getBytes());// zapisem blok
            raf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public R find(R record) {

        Block block;
        block = readBlock(rootAddress);

        for(int i = 0; i < this.height; i++){ // hladam az kym sa nedostanem na uroven listu
            Record toFound = block.findRecord(record);
            if(toFound != null){ // ak som nasiel dany rekord
                return (R) toFound;
            } else { // prejdem referenciou na potomka
                if(block.findCoveringBlock(record) != -1) {
                    block = readBlock(block.findCoveringBlock(record));
                }
                //break;
            }
        }
        this.tmp = block;
        return null; // nenasiel sa
    }

    @Override
    public void insert(R record) {
        Record rec = find(record);
        if(rec != null){
            System.out.println("Item with this key exist! Duplicity!");

        } else {

            if(this.tmp.getNumberOfRecords() != this.tmp.getNumberOfValidRecords()){ // blok je volny
                this.tmp.insert(record);
                this.tmp.setRecords(sortArray(this.tmp.getRecords()));// DOROBIT POSUVANIE REFERENCII TIEZ
                writeBlock(this.tmp);

            } else { // blok je plny a treba ho rozdelit, rozdelujeme az kym sa nenajde miesto alebo sa nezveci vyska stromu

                Block newBlock = new Block(recordType, clusterSize);
                Record median;

                Record[] array = new Record[newBlock.getNumberOfRecords()+1];

                for(int i = 0; i < this.tmp.getRecords().length; i++){
                    array[i] = this.tmp.getRecords()[i];
                }
                array[this.tmp.getNumberOfValidRecords()] = record;

                array = sortArray(array);

                int medianIndex = (int) Math.floor(array.length/2);
                median = array[medianIndex];

                // novy blok
                this.numberOfBlocks++;
                newBlock.setAddress((this.numberOfBlocks - 1)*newBlock.getSize());

                for(int j = 0; j < array.length; j++){ // vacsie ako median do noveho bloku, mensie ako median ostava
                    if(j < medianIndex){
                        this.tmp.getRecords()[j] = array[j];
                        this.tmp.getIsRecordValid()[j] = true;

                    } else if(j > medianIndex) {
                        newBlock.getRecords()[j - medianIndex - 1] = array[j];
                        newBlock.getIsRecordValid()[j - medianIndex - 1] = true;
                        newBlock.getReferencesAddresses()[j - medianIndex - 1] = this.tmp.getReferencesAddresses()[j - 1];

                        this.tmp.getReferencesAddresses()[j - 1] = -1;
                        this.tmp.getIsRecordValid()[j - 1] = false;
                        this.tmp.getRecords()[j - 1] = recordType;
                    }
                }
                while(this.tmp.getFatherAddress() != -1) { // vkladame az kym nevlozime alebo nevytvorime novy root
                    int savedAddress = this.tmp.getAddress();
                    writeBlock(this.tmp);
                    this.tmp = readBlock(this.tmp.getFatherAddress());
                    if(this.tmp.getNumberOfRecords() != this.tmp.getNumberOfValidRecords()){ // blok je volny

                        this.tmp.insert(median);
                        this.tmp.setRecords(sortArray(this.tmp.getRecords()));//zosortovat
                        int index = this.tmp.getRecordIndex(median); // index vlozeneho
                        this.tmp.moveReferences(index);
                        this.tmp.getReferencesAddresses()[index] = savedAddress; // priradim referencie
                        this.tmp.getReferencesAddresses()[index + 1] = newBlock.getAddress();

                        newBlock.setFatherAddress(this.tmp.getAddress());
                        writeBlock(newBlock);
                        writeBlock(this.tmp);
                        return;
                    }

                    newBlock.setFatherAddress(this.tmp.getAddress()); // asi adresa otca, este nezapisem lebo jeho otec bude root?
                    writeBlock(newBlock);
                    writeBlock(this.tmp);
                    if(this.tmp.getFatherAddress() != -1){
                        readBlock(this.tmp.getFatherAddress());

                    }
                }

                if(this.tmp.getFatherAddress() == -1) { // nema otca -> novy koren
                    // nema otca -> novy koren
                    Block newRootBlock = new Block(recordType, clusterSize);
                    height++;

                    this.numberOfBlocks++;
                    newRootBlock.setAddress((this.numberOfBlocks - 1) * newRootBlock.getSize());
                    this.tmp.setFatherAddress(newRootBlock.getAddress());
                    newBlock.setFatherAddress(newRootBlock.getAddress());

                    this.rootAddress = newRootBlock.getAddress();
                    newRootBlock.getReferencesAddresses()[0] = this.tmp.getAddress(); // preto lebo vytvaram novy koren
                    newRootBlock.getReferencesAddresses()[1] = newBlock.getAddress();
                    newRootBlock.insert(median);
                    newRootBlock.setRecords(sortArray(newRootBlock.getRecords()));

                    writeBlock(this.tmp);
                    writeBlock(newBlock);
                    writeBlock(newRootBlock);
                }
            }
        }
    }

    @Override
    public R update(R record) {
        Block block;
        block = readBlock(rootAddress);

        for(int i = 0; i < this.height; i++){ // hladam az kym sa nedostanem na uroven listu
            Record toFound = block.findRecord(record);
            /*if(record.compareTo(toFound) != 0){ // AK BY SOM CHCEL MENIT KLUCOVY
                return null;
            }*/
            if(toFound != null){ // ak som nasiel dany rekord
                block.updateRecord(record);
                this.tmp = block;
                writeBlock(this.tmp);
                return (R) record;
            } else { // prejdem referenciou na potomka
                if(block.findCoveringBlock(record) != -1) {
                    block = readBlock(block.findCoveringBlock(record));
                }
                //break;
            }
        }
        writeBlock(this.tmp);
        this.tmp = block;
        return null; // nenasiel sa
    }


    public Record[] sortArray(Record[] array){

        Record tmp;
        for(int i=0; i < array.length; i++){
            for(int j=1; j < (array.length - i); j++){
                if(array[j-1].compareTo(array[j]) == 1){
                    //swap
                    tmp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = tmp;
                }
            }
        }
        return array;
    }

    @Override
    public void delete(Record record) {

    }

    @Override
    public int getSize() {
        return 0;
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

    public int getRootAddress() {
        return rootAddress;
    }

    public void setRootAddress(int rootAddress) {
        this.rootAddress = rootAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public void setNumberOfBlocks(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }

    public Record getRecordType() {
        return recordType;
    }

    public void setRecordType(Record recordType) {
        this.recordType = recordType;
    }

    public Block getTmp() {
        return tmp;
    }

    public void setTmp(Block tmp) {
        this.tmp = tmp;
    }
}
