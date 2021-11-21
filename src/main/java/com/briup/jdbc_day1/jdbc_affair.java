package com.briup.jdbc_day1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc_affair {
    public static void main(String[] args) {
        t1();
        select();
    }

    public static void t1(){
        Connection conn = jdbcUtil.getConn();
        //关闭自动提交
        try {
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();

            String sql = "insert into abgs(id,name,dob) values (64,'tom1','2020-09-14')";

            statement.executeUpdate(sql);
            conn.rollback();
            conn.commit();

            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
