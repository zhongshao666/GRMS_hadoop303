package com.briup.jdbc_day2;

import com.briup.util.Util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class jdbc_prepare {
    public static void main(String[] args) {
        t4();
    }

    public static void t1() {
        Connection conn = Util.getConn();
        try {

            String sql = "insert into abgs(id,`name`,dob) values(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 54);
            ps.setString(2, "jax");
            ps.setDate(3, new Date(new java.util.Date().getTime()));

            ps.execute();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void t2() {
        Connection conn = Util.getConn();
        try {

            String sql = "delete from abgs where id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 54);

            ps.execute();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void t3() {
        Connection conn = Util.getConn();
        try {
            conn.setAutoCommit(false);
            long l1 = System.currentTimeMillis();
            String sql = "insert into abgs (id,`name`,dob) values (?,?,?)";
            String sql2 = "insert into abgs1 (id,`name`,dob) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 10000; i++) {
                if (i == 5001) {
                    ps.executeBatch();
                    ps = conn.prepareStatement(sql2);
                }

                ps.setInt(1, i);
                ps.setString(2, "tom" + i);
                ps.setDate(3, new Date(new java.util.Date().getTime()));

                ps.addBatch();

                if (i % 3000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
            long l2 = System.currentTimeMillis();
            System.out.println(l2 - l1);
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void t4(){
        Connection conn = Util.getConn();

        try {
            String sql="insert into abgs(id,name,dob) values (?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            Abgs tom = new Abgs(1, "tom", new java.util.Date());
            ps.setObject(1,tom.getId());
            ps.setObject(2,tom.getName());
            ps.setObject(3,tom.getDob());//自动 util.Date->sql.Date
            ps.execute();
            ps.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
