package com.briup.GRMS.Step5;

import org.apache.hadoop.mapreduce.Partitioner;

import javax.xml.soap.Text;

//分区
public class KeyPartitionor extends Partitioner<TupleBean, Text> {
    @Override
    public int getPartition(
            TupleBean tupleBean, //map输出的key
            Text text, //map输出的value
            int numPartitions) {//Reduce个数
        return tupleBean.getGood()./////
                hashCode()*Integer.MAX_VALUE
                %numPartitions;
    }
}