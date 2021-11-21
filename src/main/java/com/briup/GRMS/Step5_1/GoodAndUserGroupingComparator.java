package com.briup.GRMS.Step5_1;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GoodAndUserGroupingComparator extends WritableComparator {
    public GoodAndUserGroupingComparator() {
        super(TupleBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        TupleBean tupleBeanA = (TupleBean) a;
        TupleBean tupleBeanB = (TupleBean) b;
//        System.out.println("?????????????????");
        return tupleBeanA.getGood().compareTo(tupleBeanB.getGood());
    }
}
