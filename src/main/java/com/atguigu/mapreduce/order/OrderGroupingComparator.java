package com.atguigu.mapreduce.order;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-01-28 20:27
 */
public class OrderGroupingComparator extends WritableComparator {

    protected OrderGroupingComparator() {
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        OrderBean aBean = (OrderBean) a;
        OrderBean bBean = (OrderBean) b;

        return Integer.compare(aBean.getOrderId(), bBean.getOrderId());
    }
}