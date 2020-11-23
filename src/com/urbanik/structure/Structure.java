package com.urbanik.structure;

import com.urbanik.entity.Record;

public interface Structure<R extends Record<R>> {
    public R find(R record);
    public void insert(R record);
    public R update(R record);
    public void delete(R record);
    public int getSize();
}
