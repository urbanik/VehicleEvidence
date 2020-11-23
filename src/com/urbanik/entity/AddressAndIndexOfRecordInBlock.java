package com.urbanik.entity;

import com.urbanik.mapper.BaseMapper;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Date;

public class AddressAndIndexOfRecordInBlock implements Record{

    private int address;
    private int index;

    public AddressAndIndexOfRecordInBlock(int address, int index) {
        this.address = address;
        this.index = index;
    }

    public AddressAndIndexOfRecordInBlock() {
        this.address = 0;
        this.index = 0;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(this.address);
            dos.writeInt(this.index);
        } catch (Exception e) {
            System.out.println("Error at driver license serialization!");
        }
        return baos.toByteArray();
    }

    @Override
    public Object fromBytes(byte[] byteArray) {
        try {

            byte[] add = new byte[4];
            byte[] ind = new byte[4];

            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            DataInputStream dis = new DataInputStream(bais);

            dis.read(add);
            dis.read(ind);

            this.address = ByteBuffer.wrap(add).getInt();
            this.index = ByteBuffer.wrap(ind).getInt();

            return this;

        } catch (IOException ex) {
            System.out.println("Error at driver license deserialization!");
        }
        return null;
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public Object newInstance() {
        return new AddressAndIndexOfRecordInBlock();
    }

    @Override
    public BaseMapper getMapper() {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
