package com.briup.jdbc_exercise.day2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
/*        List<Teacher> list = new ArrayList<>();
        list.add(new Teacher(1, "tom", 21, 1500f));
        list.add(new Teacher(2, "tom1", 24, 1600f));
        list.add(new Teacher(3, "tom2", 22, 1700f));
        saveAll(list);*/
        update(new Teacher(1,"tom",21,6666f));
        deleteById(3);
        List<Teacher> list = findAll();
        list.forEach(System.out::println);
    }

    public static void update(Teacher teacher) {
        Connection conn = getConn();
        //创建会话
        try {

            String sql = "update teacher set name=?,age=?,salary=? where id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setObject(1, teacher.getName());
            ps.setObject(2, teacher.getAge());
            ps.setObject(3, teacher.getSalary());
            ps.setObject(4, teacher.getId());
            ps.execute();
            ps.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteById(int id) {
        Connection conn = getConn();
        //创建会话
        try {

            String sql = "delete from teacher where id='" + id + "'";
            Statement statement = conn.createStatement();
            statement.execute(sql);
            statement.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public static List<Teacher> saveAll(List<Teacher> list) {
        Connection conn = getConn();
        //创建会话
        try {

            String sql = "insert into teacher(id,name,age,salary) values (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (Teacher Teacher : list) {
                ps.setInt(1, Teacher.getId());
                ps.setString(2, Teacher.getName());
                ps.setInt(3, Teacher.getAge());
                ps.setDouble(4, Teacher.getSalary());
                ps.execute();
            }

            ps.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static List<Teacher> findAll() {
        Connection conn = getConn();
        List<Teacher> list = new ArrayList<>();
        //创建会话
        try {
            Statement statement = conn.createStatement();

            String sql = "select * from teacher";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Teacher Teacher = new Teacher();
                Teacher.setId(resultSet.getInt("id"));
                Teacher.setName(resultSet.getString("name"));
                Teacher.setAge(resultSet.getInt("age"));
                Teacher.setSalary(resultSet.getDouble("salary"));
                list.add(Teacher);
            }

            statement.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Connection getConn() {

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC&characterEncoding=utf-8";
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
