package com.briup.jdbc_day1;

import java.sql.*;

public class jdbc_prepare {
    public static void main(String[] args) throws SQLException {
        Connection conn = jdbcUtil.getConn();

        String sql = "insert into abgs(id,name,dob) values(?,?,?)";

        //获取预编译对象
        PreparedStatement ps = conn.prepareStatement(sql);

        //sql语句中?对应的数值设置上
        ps.setInt(1,666);
        ps.setString(2,"hhh");
        ps.setDate(3,new Date(new java.util.Date().getTime()));
        ps.execute();
        ps.close();
        conn.close();
        select();


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
