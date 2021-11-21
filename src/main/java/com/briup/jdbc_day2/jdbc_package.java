package com.briup.jdbc_day2;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @param null
 * @Auther zzh
 * @Description TODO 封装JDBC
 * @Date 2020/9/15 16:24
 * @return
 **/
public class jdbc_package {

    static String url = "jdbc:mysql://localhost:3306/bd2005?serverTimezone=UTC&characterEncoding=utf-8";
    static String user = "ecjtu";
    static String passwd = "123456";
    static Connection conn;
    static Statement stat;
    static PreparedStatement ps;
    static ResultSet rs;

    static {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        try {
            conn = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void save(Object o) {
        try {

            StringBuilder sb = new StringBuilder("insert into (");

            //获取类名
            Class<?> os = o.getClass();
            //类名 当 表名
            String tableNames = os.getSimpleName();

            Field[] fields = os.getDeclaredFields();

            for (Field f : fields) {
                //属性名当列名
                String colName = f.getName();
                sb.append(colName).append(",");
            }
            //删除多余问号
            sb.delete(sb.length() - 1, sb.length());
            sb.append(")").append(" ").append("values").append("(");
            for (int i = 0; i <fields.length ; i++) {
                sb.append("?").append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append(")");
            System.out.println(sb);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
