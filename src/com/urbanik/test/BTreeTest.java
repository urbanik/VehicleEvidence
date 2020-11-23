package com.urbanik.test;

import com.urbanik.entity.License;
import com.urbanik.generator.LicenseGenerator;
import com.urbanik.generator.MyGenerator;
import com.urbanik.structure.BTree;

public class BTreeTest {

    BTree bTree;
    private MyGenerator generator;

    public BTreeTest() {

        bTree = new BTree(new License(), "test", 1000);
        generator = new LicenseGenerator(2000);
        bTree.insert(generator.getRecords()[0]);
        System.out.println(generator.getRecords()[0].toString());
        bTree.insert(generator.getRecords()[1]);
        System.out.println(generator.getRecords()[1].toString());
        bTree.insert(generator.getRecords()[2]);
        System.out.println(generator.getRecords()[2].toString());
        for(int i = 3; i < 1000; i++){
            bTree.insert(generator.getRecords()[i]);
            System.out.println(generator.getRecords()[i].toString());
        }
        for(int i = 0; i < 1000; i++){
            //System.out.println(bTree.find(generator.getRecords()[i]).toString());
        }
        //bTree.inOrderRecords(bTree.getRootAddress());
    }

    public BTree getbTree() {
        return bTree;
    }

    public void setbTree(BTree bTree) {
        this.bTree = bTree;
    }
}
