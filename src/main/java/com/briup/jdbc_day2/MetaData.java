package com.briup.jdbc_day2;

import com.briup.util.Util;

import java.sql.*;

public class MetaData {
    public static void main(String[] args) {
      t4();
    }

    public static void t1(){
        Connection conn = Util.getConn();
        try {
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet catalogs = metaData.getCatalogs();

            while (catalogs.next()){
                System.out.println(catalogs.getObject(1)); //得到所有数据库名
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void t2(){
        Connection conn = Util.getConn();
        try {
            DatabaseMetaData metaData = conn.getMetaData();

            //获取表信息
            ResultSet rs = metaData.getTables("bd2005", null, null, new String[]{"TABLE", "VIEW"});

            while (rs.next()){
                System.out.print(rs.getObject(1)+"\t");
                System.out.print(rs.getObject(2)+"\t");
                System.out.print(rs.getObject(3)+"\t");
                System.out.print(rs.getObject(4));
                System.out.println();
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void t3(){
        Connection conn = Util.getConn();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            //获取表中列信息
            ResultSet rs = metaData.getColumns("bd2005",null,"abgs",null);

            while (rs.next()){
                System.out.print(rs.getObject(1)+"\t");
                System.out.print(rs.getObject(2)+"\t");
                System.out.print(rs.getObject(3)+"\t");
                System.out.print(rs.getObject(4)+"\t");
                System.out.print(rs.getObject(5)+"\t");
                System.out.print(rs.getObject(6)+"\t");
                System.out.print(rs.getObject(7)+"\t");
                System.out.print(rs.getObject(8)+"\t");
                System.out.print(rs.getObject(9)+"\t");
                System.out.print(rs.getObject(10)+"\t");
                System.out.print(rs.getObject(11)+"\t");
                System.out.print(rs.getObject(12)+"\t");
                System.out.print(rs.getObject(13)+"\t");
                System.out.print(rs.getObject(24)+"\t");
                System.out.println();
            }

            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void t4(){
        Connection conn = Util.getConn();

        try {
            Statement statement = conn.createStatement();
            String sql="select * from abgs";
            ResultSet resultSet = statement.executeQuery(sql);
            //获取结果集有关信息
            ResultSetMetaData metaData = resultSet.getMetaData();

            int len=metaData.getColumnCount();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
