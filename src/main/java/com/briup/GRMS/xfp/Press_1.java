package com.briup.GRMS.xfp;

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

public class Press_1 extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        // 启动一个任务
        ToolRunner.run(new Press_1(), args);
    }

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = new Configuration();
        String p_in = "src/main/java/com/briup/GRMS/xfp/data";
        String p_out = "src/main/java/com/briup/GRMS/xfp/result";

        Job job = null;
        job = Job.getInstance(conf, "cli");
        job.setJarByClass(this.getClass());

        job.setMapperClass(PressMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setReducerClass(PressReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path(p_in));

        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path(p_out));

        job.waitForCompletion(true);
        return 0;
    }


    public static class PressMapper extends Mapper<LongWritable, Text,
            Text, LongWritable> {

        private static final long MISSING = -9999;

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String time = line.substring(0, 4) + '/' + line.substring(5, 7) + '/' + line.substring(8, 10);
            String data = line.substring(37, 43).replaceAll("\\s+", "");//压力
            long datal = Long.parseLong(data);
            if (!data.contains("-9999")) {
                context.write(new Text(time), new LongWritable(datal));
            }
        }
    }

    public static class PressReducer extends Reducer<Text, LongWritable,
            Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values,
                              Context context) throws IOException, InterruptedException {

            long maxspeed = Long.MIN_VALUE;
            long minspeed = Long.MAX_VALUE;
            double avg = 0.0;
            long temp = 0;
            int count = 0;
            for (LongWritable v : values) {
                temp = v.get();
                if (temp > maxspeed)
                    maxspeed = temp;
            }
            //context.write(key, new DoubleWritable(avg/count));
            context.write(key, new LongWritable(temp));

        }

    }

}
