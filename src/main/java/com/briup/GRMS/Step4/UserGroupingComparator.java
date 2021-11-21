package com.briup.GRMS.Step4;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UserGroupingComparator extends WritableComparator {
    public UserGroupingComparator() {
        super(UserBuyVectorBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        UserBuyVectorBean userBuyVectorBeanA = (UserBuyVectorBean) a;
        UserBuyVectorBean userBuyVectorBeanB = (UserBuyVectorBean) b;
        return userBuyVectorBeanA.getGood().compareTo(userBuyVectorBeanB.getGood());
    }

}
