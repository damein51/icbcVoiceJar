package com.atguigu.mapreduce.table;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-01-29 17:16
 */
public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    String name;
    TableBean bean = new TableBean();
    Text text = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        // 1 获取输入文件切片
        FileSplit split = (FileSplit) context.getInputSplit();

        // 2 获取输入文件名称
        name = split.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 1 获取输入数据
        String line = value.toString();

        // 2 不同文件分别处理
        // 订单表处理
        if (name.startsWith("order")) {

            // 2.1 切割
            String[] fields = line.split("\t");

            // 2.2 封装bean对象
            bean.setOrderId(fields[0]);
            bean.setPId(fields[1]);
            bean.setAmount(Integer.parseInt(fields[2]));
            bean.setPName("");
            bean.setFlag("other");

            text.set(fields[1]);
        } else {
            // 产品表处理

            // 2.3 切割
            String[] fields = line.split("\t");

            // 2.4 封装bean对象
            bean.setPId(fields[0]);
            bean.setPName(fields[1]);
            bean.setFlag("pd");
            bean.setAmount(0);
            bean.setOrderId("");

            text.set(fields[0]);
        }

        // 3 写出
        context.write(text, bean);
    }
}