package com.briup.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    public static Connection getConn(){

        Connection connection=null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/bd2005?serverTimezone=UTC&characterEncoding=utf-8";
            String user = "ecjtu";
            String passwd = "123456";

            connection = DriverManager.getConnection(url, user, passwd);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
