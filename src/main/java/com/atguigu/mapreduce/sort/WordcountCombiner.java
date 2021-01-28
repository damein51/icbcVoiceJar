package com.atguigu.mapreduce.sort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-01-28 17:26
 */
public class WordcountCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // 1 汇总操作
        int count = 0;
        for (IntWritable v : values) {
            count += v.get();
        }

        // 2 写出
        context.write(key, new IntWritable(count));
    }
}