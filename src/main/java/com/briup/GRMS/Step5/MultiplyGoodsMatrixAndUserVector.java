package com.briup.GRMS.Step5;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//读取两个文件
public class MultiplyGoodsMatrixAndUserVector
        extends Configured implements Tool {

    /*
            20001,0	20001:3,20002:2,20005:2,20006:2,20007:1
            20002	20001:2,20002:3,20005:2,20006:2,20007:2

            20001,1	10001:1,10004:1,10005:1
            20002	10001:1,10003:1,10004:1
     */
    /*
             k             v
        20001,10001   a,20001,3
        20001,10001   a,20002,2
        20001,10001   a,20003,0
        20001,10001   a,20004,0
        20001,10001   a,20005,2
        20001,10001   a,20006,2
        20001,10001   a,20007,1

        20001,10002   a,20001,3
        20001,10002   a,20002,2
        20001,10002   a,20003,0
        20001,10002   a,20004,0
        20001,10002   a,20005,2
        20001,10002   a,20006,2
        20001,10002   a,20007,1

        20001,10003   a,20001,3
        20001,10003   a,20002,2
        20001,10003   a,20003,0
        20001,10003   a,20004,0
        20001,10003   a,20005,2
        20001,10003   a,20006,2
        20001,10003   a,20007,1
        ...
    **/
    private static final String flag = "a,";
    private static final String flag1 = "b,";

    public static class MultiplyGoodsMatrixAndUserVectorFirstMapper
            extends Mapper<LongWritable, Text, TupleBean, Text> {
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
//                    String k = line[0] + "," + i;
                    String v;
                    if (map.containsKey(Integer.toString(j))) {
                        v = flag + j + "," + map.get(Integer.toString(j));
                    } else {
                        v = flag + j + ",0";
                    }
                    TupleBean tupleBean = new TupleBean(new Text(line[0]), new Text(String.valueOf(i)));
                    context.write(tupleBean, new Text(v));
                }
            }
        }
    }

    /*
            20001,1	10001:1,10004:1,10005:1
            20002	10001:1,10003:1,10004:1
     */
    /*
      竖向，方向不同，我们需要横向
             k            v
        20001,10001   b,20001,1
        20001,10001   b,20002,1
        20001,10001   b,20003,0
        20001,10001   b,20004,0
        20001,10001   b,20005,1
        20001,10001   b,20006,1
        20001,10001   b,20007,1

        20001,20002   b,20001,0
        20001,20002   b,20002,0
        20001,20002   b,20003,1
        20001,20002   b,20004,1
        20001,20002   b,20005,0
        20001,20002   b,20006,1
        20001,20002   b,20007,0
        ...
    **/
    //自定义分组分组排序规则
    public static int col = 20001;

    public static class MultiplyGoodsMatrixAndUserVectorSecondMapper
            extends Mapper<LongWritable, Text, TupleBean, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split("\t");
            String[] vs = line[1].split(",");
            Map<String, String> map = new HashMap<>();
            for (String s : vs) {
                String[] ss = s.split(":");
                map.put(ss[0], ss[1]);
            }
//            System.out.println(map.size());
            String v;
            for (int i = 20001; i <= 20007; i++) {
                for (int j = 10001; j <= 10006; j++) {
//                    String k = i + "," + j;
                    if (map.containsKey(Integer.toString(j))) {
                        v = flag1 + col + ",1";
                    } else {
                        v = flag1 + col + ",0";
                    }
                    TupleBean tupleBean = new TupleBean(new Text(String.valueOf(i)), new Text(String.valueOf(j)));
                    context.write(tupleBean, new Text(v));
                }
            }
            col++;
        }
    }

    public static class MultiplyGoodsMatrixAndUserVectorReducer
            extends Reducer<TupleBean, Text, Text, Text> {
        @Override
        protected void reduce(TupleBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            Map<String, String> mapA = new HashMap<>();
            Map<String, String> mapB = new HashMap<>();

            for (Text value : values) {
                String[] val = value.toString().split(",");
                if ("a".equals(val[0])) {
                    mapA.put(val[1], val[2]);
                } else if ("b".equals(val[0])) {
                    mapB.put(val[1], val[2]);
                }
            }
            int result = 0;
            Iterator<String> mKeys = mapA.keySet().iterator();
            while (mKeys.hasNext()) {
                String mkey = mKeys.next();
                if (mkey == null) {// 因为mkey取的是mapA的key集合，所以只需要判断mapB是否存在即可。
                    continue;
                }
                result += Integer.parseInt(mapA.get(mkey))
                        * Integer.parseInt(mapB.get(mkey));
            }
//            String[] strs = key.toString().split(",");
//            String str = strs[1] + "," + strs[0];
            String str = key.getUser() + "," + key.getGood();
            if (result!=0)
                context.write(new Text(str), new Text(Integer.toString(result)));
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        String input1 = "src/main/java/com/briup/GRMS/Step3/result/part-r-00000";
        String input2 = "src/main/java/com/briup/GRMS/Step4/result/part-r-00000";
        String output = "src/main/java/com/briup/GRMS/Step5/result";

        Job job = Job.getInstance(conf);
        job.setJarByClass(this.getClass());

        MultipleInputs.addInputPath(job, new Path(input1),
                TextInputFormat.class,
                MultiplyGoodsMatrixAndUserVectorFirstMapper.class);

        MultipleInputs.addInputPath(job, new Path(input2),
                TextInputFormat.class,
                MultiplyGoodsMatrixAndUserVectorSecondMapper.class);

        job.setMapOutputKeyClass(TupleBean.class);
        job.setMapOutputValueClass(Text.class);
        //指定分区
//        job.setPartitionerClass(KeyPartitionor.class);
        //指定分组
//        job.setGroupingComparatorClass(GoodAndUserGroupingComparator.class);

        job.setReducerClass(
                MultiplyGoodsMatrixAndUserVectorReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : -1;
    }

    public static void main(String[] args) throws Exception {
        ToolRunner.run(
                new MultiplyGoodsMatrixAndUserVector(), args);
    }
}


