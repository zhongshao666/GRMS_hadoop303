package com.briup.GRMS.cet4;



import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


//分区
public class KeyPartitionor extends Partitioner<Bean, Text> {
    @Override
    public int getPartition(
            Bean Bean, //map输出的key
            Text text, //map输出的value
            int numPartitions) {//Reduce个数(固定)

        return Bean.getWord().
                hashCode()*Integer.MAX_VALUE
                %numPartitions;
    }
}
