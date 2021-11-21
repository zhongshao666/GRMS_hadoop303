package com.briup.jdbc_day1;


import java.sql.*;

public class JDBC {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
/*        Driver driver = new Driver();
        DriverManager.registerDriver(driver);*/

//        Class.forName("com.mysql.cj.jdbc.Driver");


        System.setProperty("jdbc.drivers","com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_class?serverTimezone=UTC&characterEncoding=utf-8",
                "ecjtu",
                "123456");
        Statement statement = conn.createStatement();

        ResultSet re = statement.executeQuery("select * from t_user");

        while (re.next()) {
            System.out.print(re.getString("username") + "\t");
            System.out.print(re.getString("username") + "\n");
        }
        conn.close();

    }
}
