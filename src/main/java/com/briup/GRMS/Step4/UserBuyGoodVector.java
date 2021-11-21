package com.briup.GRMS.Step4;

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


public class UserBuyGoodVector extends Configured implements Tool {
/*
    public static class UserBuyGoodVectorMapper extends Mapper<LongWritable, Text,UserBuyVectorBean,Text> {
*//*        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split("\t");
            String[] v2s = lines[1].split(",");
            for (String s:v2s) {
                UserBuyVectorBean userBuyVectorBean=new UserBuyVectorBean(new Text(s),new Text(lines[0]));
                System.out.println(userBuyVectorBean.toString());//
                context.write(userBuyVectorBean,new Text(lines[0]+":1"));
            }
        }*//*

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split("\t");
            String[] v2s = lines[1].split(",");
            for (String s:v2s) {
                UserBuyVectorBean userBuyVectorBean=new UserBuyVectorBean(new Text(s),new Text(lines[0]));
                System.out.println(userBuyVectorBean.toString());//
                context.write(userBuyVectorBean,new Text(lines[0]+":1"));
            }
        }
    }



    public static class UserBuyGoodVectorReducer extends Reducer<UserBuyVectorBean,Text,Text,Text> {
*//*        @Override
        protected void reduce(UserBuyVectorBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String str = "";
            for (Text t:values) {
                str += t.toString()+",";
            }
            String value=str.substring(0,str.length()-1);
            System.out.println(key.toString());//
            context.write(key.getGood(),new Text(value));
        }*//*

        @Override
        protected void reduce(UserBuyVectorBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String str = "";
            for (Text t:values) {
                str += t.toString()+",";
            }
            String value=str.substring(0,str.length()-1);
            System.out.println(key.toString());//
            context.write(key.getGood(),new Text(value));
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        //
        Configuration configuration=getConf();
        String input = "src/main/java/com/briup/GRMS/Step1/result/part-r-00000";//数据来源
        String output="src/main/java/com/briup/GRMS/Step4/result";//数据去向

        //构建mapReduce job作业
        Job job = Job.getInstance(configuration);
        job.setJobName("UserBuyGoodVector");
        job.setJarByClass(UserBuyGoodVector.class);//设置主类



        //设置mapper函数
        job.setMapperClass(UserBuyGoodVectorMapper.class);
        job.setMapOutputKeyClass(UserBuyVectorBean.class);
        job.setMapOutputValueClass(Text.class);

        //给Job设置以上自定义GroupingComparator
        job.setGroupingComparatorClass(UserGroupingComparator.class);

        //设置reducer函数
        job.setReducerClass(UserBuyGoodVectorReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //数据输入形式          key:偏移量      value:每行内容
        job.setInputFormatClass(TextInputFormat.class);
        //输出形式
        job.setOutputFormatClass(TextOutputFormat.class);

        //绑定输入输出路径
        TextInputFormat.addInputPath(job,new Path(input));
        TextOutputFormat.setOutputPath(job,new Path(output));

        return job.waitForCompletion(true)?0:1;

    }*/


    public static class UserBuyGoodVectorMapper extends Mapper<LongWritable, Text, UserBuyVectorBean, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split("\t");
            String v2 = lines[0];
            String[] k2s = lines[1].split(",");
            for (String s : k2s) {
                UserBuyVectorBean Bean = new UserBuyVectorBean(new Text(s), new Text(v2));
                context.write(Bean, new Text(v2 + ":1,"));
            }
        }
    }

    public static class UserBuyGoodVectorReducer extends Reducer<UserBuyVectorBean, Text, Text, Text> {
        @Override
        protected void reduce(UserBuyVectorBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String line = "";
            for (Text t : values) {
                line += t.toString();
            }
            line = line.substring(0, line.length() - 1);
            context.write(new Text(key.getGood()), new Text(line));
        }
    }


    @Override
    public int run(String[] args) throws Exception {
        Configuration configuration = getConf();
        String input = "src/main/java/com/briup/GRMS/Step1/result/part-r-00000";
        String output = "src/main/java/com/briup/GRMS/Step4/result";

        Job job = Job.getInstance(configuration);
        job.setJobName("UserBuyGoodVector");
        job.setJarByClass(UserBuyGoodVector.class);

        job.setMapperClass(UserBuyGoodVectorMapper.class);
        job.setMapOutputKeyClass(UserBuyVectorBean.class);
        job.setMapOutputValueClass(Text.class);

        //给Job设置以上自定义GroupingComparator
        job.setGroupingComparatorClass(UserGroupingComparator.class);

        job.setReducerClass(UserBuyGoodVectorReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextInputFormat.addInputPath(job, new Path(input));
        TextOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        //主类对象
        ToolRunner.run
                (new UserBuyGoodVector(), args);
    }
}
