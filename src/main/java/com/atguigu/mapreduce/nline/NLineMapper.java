package com.atguigu.mapreduce.nline;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-01-29 11:18
 */
public class NLineMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

    private Text k = new Text();
    private LongWritable v = new LongWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context)	throws IOException, InterruptedException {

        // 1 获取一行
        String line = value.toString();

        // 2 切割
        String[] splited = line.split(" ");

        // 3 循环写出
        for (String s : splited) {

            k.set(s);

            context.write(k, v);
        }
    }
}