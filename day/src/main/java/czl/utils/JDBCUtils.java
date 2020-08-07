package main.java.czl.utils;


import org.apache.commons.dbutils.DbUtils;
import org.junit.Test;



import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/5
 * @Description:
 */
public class JDBCUtils {

            /*InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("main/resources/druid.properties");
            //创建配置文件对象
            Properties properties = new Properties();
            properties.load(is);
            //创建连接池对象
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            //获取连接
            Connection conn = dataSource.getConnection();
            System.out.println(conn);*/




    /**
     * 使用Druid数据库连接池技术
     */
    public static DataSource dataSource;
    static{
        try {
            //加载配置文件
            Properties properties = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("main/resources/druid.properties");
            properties.load(is);
            //创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接池
     * @return
     * @throws Exception
     */
    public static Connection getConnection()throws Exception{
        Connection conn = dataSource.getConnection();
        return conn;
    }

    /**
     * 资源关闭
     * @param conn
     */
    public static void closeResource(Connection conn){
        DbUtils.closeQuietly(conn);
    }

}
