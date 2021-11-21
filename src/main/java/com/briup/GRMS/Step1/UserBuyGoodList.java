package com.briup.GRMS.Step1;
/*
 * @Auther zzh
 * @Description  TODO
 * @Date 2020/8/21 9:20
 * @param null
 * @return
 * 原始数据
 * 1001 20005 1
 *
 *
 * 结果
 * 10001 20003,20004,20006
 * 10003 20002
 *
 **/


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

public class UserBuyGoodList extends Configured implements Tool {

    public static class UserBuyGoodListMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//            super.map(key, value, context);
            String[] lines = value.toString().split("\t");
            String k1 = lines[0];
            String v1 = lines[1];

            //输出给reduce  String转Text
            context.write(new Text(k1), new Text(v1));

        }
    }

    /*
      101 201
      101 202   101 (201,202)
      102 201
      102 203   102 (201,202)

      分区:决定map处理完的数据进入到哪个reduce
      分组:将进入相同reduce的key-value数据进行分组，相同key归一组，value值合并
      排序:基于key的大小，对所有分组完的数据进行排序
     */

    public static class UserBuyGoodListReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//            super.reduce(key, values, context);
            String str = "";
            for (Text t : values) {
                str += t.toString() + ",";
            }
            String value = str.substring(0, str.length() - 1);
            context.write(key, new Text(value));
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        //
        Configuration configuration = getConf();
        String input = "src/main/java/com/briup/GRMS/data";//数据来源
        String output = "src/main/java/com/briup/GRMS/Step1/result";//数据去向

        //构建mapReduce job作业
        Job job = Job.getInstance(configuration);
        job.setJobName("UserBuyGoodList");
        job.setJarByClass(UserBuyGoodList.class);//设置主类

        //设置mapper函数
        job.setMapperClass(UserBuyGoodListMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        //设置reducer函数
        job.setReducerClass(UserBuyGoodListReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //数据输入形式          key:偏移量      value:每行内容
        job.setInputFormatClass(TextInputFormat.class);
        //输出形式
        job.setOutputFormatClass(TextOutputFormat.class);

        //绑定输入输出路径
        TextInputFormat.addInputPath(job, new Path(input));
        TextOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {
        //主类对象
        ToolRunner.run
                (new UserBuyGoodList(), args);
    }
}
