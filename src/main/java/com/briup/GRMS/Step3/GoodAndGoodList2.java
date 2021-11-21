package com.briup.GRMS.Step3;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class GoodAndGoodList2 extends Configured implements Tool {

    /*
          key     value
    20001	20001	3
    20001	20002	2
    20001	20005	2
    20001	20006	2
    20001	20007	1
    20002	20001	2
    20002	20002	3
    20002	20005	2
    20002	20006	2
    20002	20007	2
    20003	20003	1
    20003	20004	1
     */
    public static class GoodAndGoodList2Mapper extends Mapper<LongWritable, Text, GoodBean, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split("\t");
            String k2 = lines[0];
            String v2 = lines[1] + ":" + lines[2] + ",";
            GoodBean goodBean = new GoodBean(new Text(k2), new Text(v2));
            context.write(goodBean, new Text(v2));
        }
    }

    public static class GoodAndGoodList2Reducer extends Reducer<GoodBean, Text, Text, Text> {
        @Override
        protected void reduce(GoodBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String line = "";
            for (Text t : values) {
                line += t.toString();
            }
            line = line.substring(0, line.length() - 1);
            context.write(key.getGood1(), new Text(line));
        }

    }
    /*
    20001	20001:3,20002:2,20005:2,20006:2,20007:1
    20002	20001:2,20002:3,20005:2,20006:2,20007:2
    20003	20003:1,20004:1,20006:1
    20004	20003:1,20004:2,20006:1,20007:1
    20005	20001:2,20002:2,20005:2,20006:2,20007:1
    20006	20001:2,20002:2,20003:1,20004:1,20005:2,20006:3,20007:1
    20007	20001:1,20002:2,20004:1,20005:1,20006:1,20007:3
     */


    @Override
    public int run(String[] args) throws Exception {
        Configuration configuration = getConf();
        String input = "src/main/java/com/briup/GRMS/Step2/result/part-r-00000";
        String output = "src/main/java/com/briup/GRMS/Step3/result";

        Job job = Job.getInstance(configuration);
        job.setJobName("GoodAndGoodList2");
        job.setJarByClass(GoodAndGoodList2.class);

        job.setMapperClass(GoodAndGoodList2Mapper.class);
        job.setMapOutputKeyClass(GoodBean.class);
        job.setMapOutputValueClass(Text.class);

        //指定分区
        job.setPartitionerClass(KeyPartitionor.class);
        //给Job设置以上自定义GroupingComparator
        job.setGroupingComparatorClass(GoodGroupingComparator.class);

        job.setReducerClass(GoodAndGoodList2Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextInputFormat.addInputPath(job, new Path(input));
        TextOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        ToolRunner.run(new GoodAndGoodList2(), args);
    }
}
