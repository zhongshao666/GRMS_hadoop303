package com.briup.jdbc_day1;

import java.sql.*;

public class jdbc_test1 {

    public static void main(String[] args) {
        new D2test().select();
    }
}

class D2test {

    void cr() {
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();
            String sql = "create table abgs(id int,name varchar(20),dob date)";
            statement.execute(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void alter(){
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();
            String sql = "alter table abgs change name name varchar(30)"; //20修改成30
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insert2(){
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into abgs(id,name) values (1,'tom')";
            String sql1 = "insert into abgs(id,name) values (2,'lisi')";
            statement.executeUpdate(sql);
            statement.executeUpdate(sql1);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insert22(){
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();
            int id=20;
            String name="tom123";
            String sql = "insert into abgs(id,name) values ("+id+",'"+name+"')";
            String sql1 = "insert into abgs(id,name) values ("+(id+1)+",'"+name+"')";
            statement.executeUpdate(sql);
            statement.executeUpdate(sql1);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void delete(){
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();

            String sql = "delete from abgs where id = 101";

            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void select(){
        Connection conn = jdbcUtil.getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();

            String sql = "select * from abgs";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.print(resultSet.getString("name"));
                System.out.println();
            }

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}