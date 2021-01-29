package com.atguigu.mapreduce.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @program: damein
 * @description:
 * @author: lidongmin
 * @create: 2021-01-29 17:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableBean implements Writable {

    /**
     * 订单id
     */
    private String orderId;
    // 产品id
    private String pId;
    // 产品数量
    private int amount;
    // 产品名称
    private String pName;
    // 表的标记
    private String flag;


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeUTF(pId);
        out.writeInt(amount);
        out.writeUTF(pName);
        out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF();
        this.pId = in.readUTF();
        this.amount = in.readInt();
        this.pName = in.readUTF();
        this.flag = in.readUTF();
    }

    @Override
    public String toString() {
        return orderId + "\t" + pName + "\t" + amount + "\t";
    }
}