package com.briup.GRMS.Step5_1;

import org.apache.hadoop.mapreduce.Partitioner;

import javax.xml.soap.Text;

/*
//分区
public class KeyPartitionor extends Partitioner<TupleBean, Text> {
    @Override
    public int getPartition(
            TupleBean tupleBean, //map输出的key
            Text text, //map输出的value
            int numPartitions) {//Reduce个数

        //// 相同good值的必定会到同一个Reducer上
        return tupleBean.getGood()./////
                hashCode()*Integer.MAX_VALUE
                %numPartitions;
    }
}*/
