package com.briup.jdbc_exercise.day1;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class T1 {



    public static void main(String[] args) {
/*        List<Student> list = new ArrayList<>();
        list.add(new Student(66, "wangwu", 21));
        list.add(new Student(67, "wanglaowu", 22));
        saveAll(list);
        select();*/
        List<Student> list=findAll();
        list.forEach(System.out::println);
        select();
    }

    static void create(){
        Connection conn = getConn();
        try {
            Statement statement = conn.createStatement();
            String sql="create table t_student(" +
                    "id int primary key," +
                    "name varchar(100) not null," +
                    "age int" +
                    ")";
            statement.execute(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void insert(){
        Connection conn = getConn();
        try {
            Statement statement = conn.createStatement();
            for (int i = 1; i <11 ; i++) {
                String sql="insert into t_student(id,name,age) values("+i+",'tom',"+(i+10)+")";
                statement.executeUpdate(sql);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void update(){
        Connection conn = getConn();
        try {
            Statement statement = conn.createStatement();
            String sql="update  t_student set name='张三' where id=2";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static List<Student> findAll(){
        Connection conn = getConn();
        List<Student> list = new ArrayList<>();
        //创建会话
        try {
            Statement statement = conn.createStatement();

            String sql = "select * from t_student";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                list.add(student);
            }

            statement.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    static List<Student> saveAll(List<Student> list){
        Connection conn = getConn();
        //创建会话
        try {

            String sql = "insert into t_student(id,name,age) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            for (Student student : list) {
                ps.setInt(1,student.getId());
                ps.setString(2,student.getName());
                ps.setInt(3,student.getAge());
                ps.execute();
            }

            ps.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }


    static void select(){
        Connection conn = getConn();
        //创建会话
        try {
            Statement statement = conn.createStatement();

            String sql = "select * from t_student";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.print(resultSet.getString("name")+"\t");
                System.out.print(resultSet.getInt("age"));
                System.out.println();
            }

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

class Student{
    int id;
    String name;
    int age;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
