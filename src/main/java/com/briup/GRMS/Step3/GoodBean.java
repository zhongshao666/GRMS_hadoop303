package com.briup.GRMS.Step3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GoodBean implements WritableComparable<GoodBean> {
//    good1  good2,good2,good2,good2,good2,good2
//    20001	20001:3,20002:2,20005:2,20006:2,20007:1

    private Text good1;
    private Text good2;

    public GoodBean() {
    }

    public GoodBean(Text good1, Text good2) {
        this.good1 = good1;
        this.good2 = good2;
    }

    public void set(Text good1, Text good2) {
        this.good1 = good1;
        this.good2 = good2;
    }

    public Text getGood1() {
        return good1;
    }

    public void setGood1(Text good1) {
        this.good1 = good1;
    }

    public Text getGood2() {
        return good2;
    }

    public void setGood2(Text good2) {
        this.good2 = good2;
    }

    @Override
    public int compareTo(GoodBean o) {//用于排序
        if (o == null)
            throw new RuntimeException();
        else if (this.good1.compareTo(o.getGood1()) == 0)
            return good2.compareTo(o.getGood2());//
        return this.good1.compareTo(o.getGood1());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(good1.toString());
        out.writeUTF(good2.toString());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        good1 = new Text(in.readUTF());
        good2 = new Text(in.readUTF());
    }


}
