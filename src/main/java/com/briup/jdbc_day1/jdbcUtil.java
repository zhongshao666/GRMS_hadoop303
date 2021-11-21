package com.briup.jdbc_day1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcUtil {
    public static Connection getConn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url="jdbc:mysql://localhost:3306/bd2005?serverTimezone=UTC&characterEncoding=utf-8";
        String user="ecjtu";
        String passwd="123456";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(connection);

        return connection;
    }
}
