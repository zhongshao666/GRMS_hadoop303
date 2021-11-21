package com.briup.GRMS.Step5_2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//读取两个文件
public class MultiplyGoodsMatrixAndUserVector
        extends Configured implements Tool {

    /*
            20001,0	20001:3,20002:2,20005:2,20006:2,20007:1
            20002	20001:2,20002:3,20005:2,20006:2,20007:2
     */
    public static class MultiplyGoodsMatrixAndUserVectorFirstMapper
            extends Mapper<LongWritable, Text,TextTuple,Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

                        String[] line = value.toString().split("\t");
            String[] vs = line[1].split(",");
            Map<String, String> map = new HashMap<>();
            for (String s : vs) {
                String[] ss = s.split(":");
                map.put(ss[0], ss[1]);
            }

            for (int i = 10001; i <= 10006; i++) {
                for (int j = 20001; j <= 20007; j++) {

                    String v;
                    if (map.containsKey(Integer.toString(j))) {
//                        v = flag + j + "," + map.get(Integer.toString(j));
                    } else {
//                        v = flag + j + ",0";
                    }

//                    context.write();
                }
            }


        }
    }
    /*
            20001,1	10001:1,10004:1,10005:1
            20002	10001:1,10003:1,10004:1
     */
    //自定义分组分组排序规则
    public static class MultiplyGoodsMatrixAndUserVectorSecondMapper
            extends Mapper<LongWritable, Text,TextTuple,Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        }
    }

    public static class MultiplyGoodsMatrixAndUserVectorReducer
            extends Reducer<TextTuple,Text,Text,Text> {
        @Override
        protected void reduce(TextTuple key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf=getConf();
        String input1="";
        String input2="";
        String output="";

        Job job=Job.getInstance(conf);
        job.setJarByClass(this.getClass());

        MultipleInputs.addInputPath(job,new Path(input1),
                TextInputFormat.class,
                MultiplyGoodsMatrixAndUserVectorFirstMapper.class);

        MultipleInputs.addInputPath(job,new Path(input2),
                TextInputFormat.class,
                MultiplyGoodsMatrixAndUserVectorSecondMapper.class);

        job.setMapOutputKeyClass(TextTuple.class);
        job.setMapOutputValueClass(Text.class);
        //指定分区
        job.setPartitionerClass(KeyPartitionor.class);
        //指定分组
        job.setGroupingComparatorClass(KeyGroup.class);

        job.setReducerClass(
                MultiplyGoodsMatrixAndUserVectorReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,new Path(output));

        return job.waitForCompletion(true)?0:-1;
    }

    public static void main(String[] args) throws Exception{
        ToolRunner.run(
                new MultiplyGoodsMatrixAndUserVector(),args);
    }
}
