package com.briup.jdbc_day1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc_batch {
    public static void main(String[] args) {
        Connection conn = jdbcUtil.getConn();

        try {
            conn.setAutoCommit(false);

            Statement statement = conn.createStatement();
            String sql="";
            String sql1="";
            statement.addBatch(sql);
            statement.addBatch(sql1);

            statement.executeBatch();

            conn.commit();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
