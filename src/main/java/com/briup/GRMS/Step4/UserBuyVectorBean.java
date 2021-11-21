package com.briup.GRMS.Step4;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class UserBuyVectorBean implements WritableComparable<UserBuyVectorBean> {


    private Text good;
    private Text user;


    public UserBuyVectorBean() {
    }

    public UserBuyVectorBean(Text good, Text user) {
        this.good = good;
        this.user = user;
    }

    public void set(Text good, Text user) {
        this.good = good;
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
    public int compareTo(UserBuyVectorBean o) {
        if (o == null)
            throw new RuntimeException();
        else if (this.good.compareTo(o.getGood()) == 0)
            return user.compareTo(o.getUser());
        return this.good.compareTo(o.getGood());
/*        int i = 0;
        if (o instanceof UserBuyVectorBean) {
            UserBuyVectorBean cc = (UserBuyVectorBean) o;
            i = this.user.compareTo(cc.getUser());
            if (i == 0) {
                return this.good.compareTo(cc.getGood());
            }
        }
        return i;*/

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

    @Override
    public String toString() {
        return "UserBuyVectorBean{" +
                "good=" + good +
                ", user=" + user +
                '}';
    }
}
