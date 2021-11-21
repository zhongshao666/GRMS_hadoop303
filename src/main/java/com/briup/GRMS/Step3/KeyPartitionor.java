package com.briup.GRMS.Step3;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


//分区
public class KeyPartitionor extends Partitioner<GoodBean, Text> {
    @Override
    public int getPartition(
            GoodBean goodBean, //map输出的key
            Text text, //map输出的value
            int numPartitions) {//Reduce个数(固定)

        return goodBean.getGood1().
                hashCode()*Integer.MAX_VALUE
                %numPartitions;
    }
}
