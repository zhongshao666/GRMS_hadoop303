package com.briup.GRMS;
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

public class WordCount extends Configured
        implements Tool {
    public static void main(String[] args)
            throws Exception{
        ToolRunner.run
                (new WordCount(), args);
    }
    public int run(String[] args) throws Exception {
        //1 获得conf
        Configuration conf = getConf();
        String input="src/main/java/com/briup/GRMS/word";
        String output="src/main/java/com/briup/GRMS/result";
        //2 声明job
        Job job = Job.getInstance(conf);
        job.setJarByClass(this.getClass());
        //3 设置job mapper
        job.setMapperClass(WCMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //4 设置job reducer
        job.setReducerClass(WCReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //5 设置输入输出路径和类型
        job.setInputFormatClass(
                TextInputFormat.class);
        job.setOutputFormatClass(
                TextOutputFormat.class);
        TextInputFormat.addInputPath
                (job, new Path(input));
        TextOutputFormat.setOutputPath
                (job, new Path(output));
        //6 提交任务
        job.waitForCompletion(true);
        return 0;
    }

    public static class WCMapper
            extends Mapper<LongWritable, Text,
                        Text, IntWritable> {
        @Override
        protected void map(
                LongWritable key,
                Text value,
                Mapper<LongWritable, Text,
                        Text, IntWritable>.Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(" ");
            for (String word : words) {
//				char x = word
//					.charAt(word.length()-1);
//				if(x ==','|| x=='.'||x=='"'
//					||x=='\''||x=='?'||x=='!') {
//					word = word
//					.substring(0, word.length()-1);
//				}
                context.write
                        (new Text(word),
                                new IntWritable(1));
            }
        }
    }

    public static class WCReducer
            extends Reducer<Text, IntWritable,
                        Text, IntWritable> {
        @Override
        protected void reduce
                (Text key, Iterable<IntWritable> values,
                 Reducer<Text, IntWritable,
                         Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable v : values) {
                sum += v.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }
}