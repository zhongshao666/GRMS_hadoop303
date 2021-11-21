package com.briup.GRMS.cet4;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Random;

public class Bean implements WritableComparable<Bean> {
//    word  good2,good2,good2,good2,good2,good2
//    20001	20001:3,20002:2,20005:2,20006:2,20007:1

    private Text word;
//    private Text three;


    public Bean() {
    }

    public Bean(Text word) {
        this.word = word;
//        this.three=new Text(this.word.toString().substring(2, 3));


    }

    public Text getWord() {
        return word;
    }

    public void setWord(Text word) {
        this.word = word;
    }


    public int getLen(){
        return this.word.getLength();
    }

    public String getThree(){
        return this.word.toString().substring(2, 3);
    }

    @Override
    public int compareTo(Bean o) {//用于排序
        if (o == null) {
            throw new RuntimeException();
        }


//        return 1;

/*        else if (this.getLen()==o.getLen())
                return this.getThree().compareTo(o.getThree());
        else return this.word.getLength()>=o.word.getLength()?0:1;*/
        return (int) (Math.random() * 2);

/*        else if (this.word.getLength() < o.word.getLength())
            return 1;
        else return 0;*/

//        return this.word.compareTo(o.getWord());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(word.toString());

    }

    @Override
    public void readFields(DataInput in) throws IOException {
        word = new Text(in.readUTF());
    }

/*    public static void main(String[] args) {
        Bean bean1 = new Bean(new Text("abdc45"));
        System.out.println(bean1.getLen());
        System.out.println(bean1.getThree());

    }*/

}
