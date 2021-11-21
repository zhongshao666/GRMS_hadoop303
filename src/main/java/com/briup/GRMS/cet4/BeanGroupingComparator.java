package com.briup.GRMS.cet4;


import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class BeanGroupingComparator extends WritableComparator {
    /**
     * 构造函数, 告知自定义Bean类
     */
    public BeanGroupingComparator() {
        super(Bean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Bean bean1 = (Bean) a;
        Bean bean2 = (Bean) b;

        return (bean2.getLen() <= (bean1.getLen()))?0:1;//只用于分组，不用于排序
}
}
