package com.briup.GRMS.Step3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GoodGroupingComparator extends WritableComparator {
    /**
     * 构造函数, 告知自定义Bean类
     */
    public GoodGroupingComparator() {
        super(GoodBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        GoodBean goodA = (GoodBean) a;
        GoodBean goodB = (GoodBean) b;
        //调用Bean中CompareTo
        return goodA.getGood1().compareTo(goodB.getGood1());//只用于分组，不用于排序
    }
}
