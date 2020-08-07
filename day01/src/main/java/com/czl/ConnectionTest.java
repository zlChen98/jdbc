package main.java.com.czl;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: jdbc_01
 * @Author: 陈志梁
 * @Data: 2020/8/3
 * @Description:
 */
public class ConnectionTest {
    //将数据库需要的信息放置在配置文件中
    @Test
    public void test5() throws  Exception {
        //读取配置文件
        InputStream resourceAsStream = ConnectionTest.class.getClassLoader().getResourceAsStream("main/resources/jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        //加载驱动
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }

    @Test
    public void test1() throws SQLException {
        Driver driver=new com.mysql.jdbc.Driver();
        String url="jdbc:mysql://localhost:3306/myproject";
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    @Test
    public void test2() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //获取Driver的实现类对象
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //提供要连接的数据库
        String url="jdbc:mysql://localhost:3306/myproject";
        //提供用户名密码
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        //获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    @Test
    public void test3() throws  Exception {
        //获取Driver的实现类对象
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //提供要基本信息
        String url="jdbc:mysql://localhost:3306/myproject";
        String user="root";
        String password="root";
        // 注册驱动
        DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    @Test
    public void test4() throws  Exception {
        //提供要基本信息
        String url="jdbc:mysql://localhost:3306/myproject";
        String user="root";
        String password="root";
        //加载Driver
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }


}


