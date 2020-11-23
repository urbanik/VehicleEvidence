package com.urbanik.entity;

import com.urbanik.mapper.BaseMapper;

public interface Record<R> extends Comparable<Record>{


    byte[] getBytes();

    R fromBytes(byte[] byteArray);

    int getSize();

    R newInstance();

    BaseMapper getMapper();

}
