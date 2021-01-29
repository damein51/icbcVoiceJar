package com.atguigu.mapreduce.outputformat;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-01-29 16:43
 */
public class FilterMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

    @Override
    protected void map(LongWritable key, Text value, Context context)	throws IOException, InterruptedException {

        // 写出
        context.write(value, NullWritable.get());
    }
}