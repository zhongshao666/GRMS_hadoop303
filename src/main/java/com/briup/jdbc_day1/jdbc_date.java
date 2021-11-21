package com.briup.jdbc_day1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class jdbc_date {
    public static void main(String[] args) {
        t2();
//        t1();
//        select();
    }

    public static void t1(){
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();

            String sql = "insert into abgs(id,name,dob) values (6,'tom1','2020-09-14')";

            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void t2(){
        Date date = new Date();
        System.out.println("date = " + date);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        System.out.println("format = " + format);
        //String->Date
        String msg="2020 16";
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy HH");
        try {
            Date format1=sdf1.parse(msg);
            System.out.println("format1 = " + format1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //util.Date -> sql.Date

        System.out.println(date.getTime());
        java.sql.Date date1=new java.sql.Date(date.getTime());
        System.out.println("date1.toString() = " + date1.toString());
    }

    public static void select(){
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();

            String sql = "select * from abgs";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.print(resultSet.getString("name")+"\t");
                System.out.print(resultSet.getString("dob"));

                System.out.println();
            }

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
