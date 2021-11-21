package com.briup.GRMS.cet4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class Cet4 extends Configured implements Tool {


    public static class GoodAndGoodList2Mapper extends Mapper<LongWritable, Text, Bean, Bean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            Bean bean = new Bean(new Text(value));
            context.write(bean, bean);
        }
    }

    public static class GoodAndGoodList2Reducer extends Reducer<Bean, Bean, Text, NullWritable> {
/*        @Override
        protected void reduce(Bean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String line = "";
            for (Text t : values) {
                line += t.toString();
            }
            line = line.substring(0, line.length() - 1);
            context.write(key.getWord(), NullWritable.get());
        }*/

        @Override
        protected void reduce(Bean key, Iterable<Bean> values, Context context) throws IOException, InterruptedException {
            Text text ;
            for (Bean bean: values) {
                text =new Text( bean.getWord());
                context.write(text, NullWritable.get());
            }
        }
    }


    @Override
    public int run(String[] args) throws Exception {
        Configuration configuration = getConf();
        String input = "src/main/java/com/briup/GRMS/cet4/mid.txt";
        String output = "src/main/java/com/briup/GRMS/cet4/result";

        Job job = Job.getInstance(configuration);
        job.setJobName("cet4");
        job.setJarByClass(Cet4.class);

        job.setMapperClass(GoodAndGoodList2Mapper.class);
        job.setMapOutputKeyClass(Bean.class);
        job.setMapOutputValueClass(Bean.class);

        //指定分区
//        job.setPartitionerClass(KeyPartitionor.class);
        //给Job设置以上自定义GroupingComparator
//        job.setGroupingComparatorClass(BeanGroupingComparator.class);

        job.setReducerClass(GoodAndGoodList2Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextInputFormat.addInputPath(job, new Path(input));
        TextOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Cet4(), args);
    }
}