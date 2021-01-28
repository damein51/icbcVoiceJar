package com.atguigu.mapreduce.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-01-28 17:47
 */
@Data
public class OrderBean implements WritableComparable<OrderBean> {

    /**
     * 订单id号
     */
    private int orderId;
    //
    /**
     * 价格
     */
    private double price;

    public OrderBean() {
        super();
    }

    public OrderBean(int orderId, double price) {
        super();
        this.orderId = orderId;
        this.price = price;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(orderId);
        out.writeDouble(price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readInt();
        price = in.readDouble();
    }

    @Override
    public String toString() {
        return orderId + "\t" + price;
    }

    /**
     * 二次排序
     */
    @Override
    public int compareTo(OrderBean o) {

        int result;

        if (orderId > o.getOrderId()) {
            result = 1;
        } else if (orderId < o.getOrderId()) {
            result = -1;
        } else {
            // 价格倒序排序
            result = price > o.getPrice() ? -1 : 1;
        }

        return result;
    }
}