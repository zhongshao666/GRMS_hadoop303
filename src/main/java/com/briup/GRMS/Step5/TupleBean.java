package com.briup.GRMS.Step5;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TupleBean implements WritableComparable<TupleBean> {

    private Text good;//20001
    private Text user;

    public TupleBean(){}
    public TupleBean(Text good,Text user){
        this.good=good;
        this.user = user;
    }

    public void set(Text good,Text user){
        this.good=good;
        this.user = user;
    }

    public Text getGood() {
        return good;
    }

    public void setGood(Text good) {
        this.good = good;
    }

    public Text getUser() {
        return user;
    }

    public void setUser(Text user) {
        this.user = user;
    }

    @Override
    public int compareTo(TupleBean o) {
        System.out.println("cccccccccccc");
        if (o == null)
            throw new RuntimeException();
        else if (this.user.compareTo(o.getUser()) == 0)
            return good.compareTo(o.getGood());
        return this.user.compareTo(o.getUser());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(good.toString());
        out.writeUTF(user.toString());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        good = new Text(in.readUTF());
        user = new Text(in.readUTF());
    }
}
