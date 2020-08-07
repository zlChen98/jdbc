package main.java.com.czl.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/4
 * @Description:
 */
public class JDBCUtils {
    /**
     *  获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("main/resources/jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        //加载驱动
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * .资源关闭
     * @param conn
     * @param ps
     */
    public static  void closeResource(Connection conn, Statement ps){
        try {
            if (ps!=null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  void closeResource(Connection conn, Statement ps, ResultSet rs){
        try {
            if (ps!=null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs!=null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Druid数据库连接池技术
     */
    private static DataSource source;
    static{
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("main/resources/druid.properties");
            pros.load(is);
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection1()throws Exception{
        Connection conn = source.getConnection();
        return conn;
    }

}
