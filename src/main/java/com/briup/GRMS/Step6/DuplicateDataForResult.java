package com.briup.GRMS.Step6;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class DuplicateDataForResult extends Configured implements Tool {

    public static class DuplicateDataForResultFirstMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] s = value.toString().split("\\s+");
            String[] ss = s[0].split(",");
            String k = ss[0] + "\t" + ss[1];
            context.write(new Text(k), new Text(s[1]));
        }
    }

    public static class DuplicateDataForResultSecondMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] s = value.toString().split("\t");
            String k = s[0] + "\t" + s[1];
            String v = s[2];
            context.write(new Text(k), new Text(v));
        }
    }

    public static class DuplicateDataForResultReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int i = 0;
            Text s = null;
            for (Text v : values) {
                System.out.println(key.toString() + ":" + v.toString());
                ++i;
                s = v;
            }
            if (i == 1) {
                System.out.println("save");
                context.write(key, s);
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        String input1 = "src/main/java/com/briup/GRMS/Step5_1/result/part-r-00000";
        String input2 = "src/main/java/com/briup/GRMS/data";
        String output = "src/main/java/com/briup/GRMS/Step6/result";

        Job job = Job.getInstance(conf);
        job.setJarByClass(this.getClass());

        MultipleInputs.addInputPath(job, new Path(input1),
                TextInputFormat.class,
                DuplicateDataForResult.DuplicateDataForResultFirstMapper.class);

        MultipleInputs.addInputPath(job, new Path(input2),
                TextInputFormat.class,
                DuplicateDataForResult.DuplicateDataForResultSecondMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //指定分区
//        job.setPartitionerClass(.class);
        //指定分组
//        job.setGroupingComparatorClass(.class);

        job.setReducerClass(
                DuplicateDataForResult.DuplicateDataForResultReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : -1;
    }

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new DuplicateDataForResult(), args);
    }
}
