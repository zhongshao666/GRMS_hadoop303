package com.briup.GRMS.Step2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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

public class GoodAndGoodList extends Configured implements Tool {

    public static class GoodAndGoodListMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split("\t");
            String[] goods = lines[1].split(",");
            for (String good1 : goods) {
                for (String good : goods) {
                    context.write(new Text(good1 + "\t" + good), new IntWritable(1));
                }
            }
        }
    }

    public static class GoodAndGoodListReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable v : values) {
                sum += v.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }


    @Override
    public int run(String[] args) throws Exception {
        Configuration configuration = getConf();
        String input = "src/main/java/com/briup/GRMS/Step1/result/part-r-00000";
        String output = "src/main/java/com/briup/GRMS/Step2/result";

        Job job = Job.getInstance(configuration);
        job.setJobName("GoodAndGoodList");
        job.setJarByClass(GoodAndGoodList.class);

        job.setMapperClass(GoodAndGoodListMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(GoodAndGoodListReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextInputFormat.addInputPath(job, new Path(input));
        TextOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        ToolRunner.run(new GoodAndGoodList(), args);
    }
}
