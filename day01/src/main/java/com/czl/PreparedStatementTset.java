package main.java.com.czl;

import main.java.com.czl.utils.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @program: jdbc_01
 * @Author: 陈志梁
 * @Data: 2020/8/3
 * @Description:
 */
public class PreparedStatementTset {

    @Test
    public void test(){
      /*  String sql="delete from customers where id=?";
        update(sql,3);*/
        String sql1="update customers set name=? where id=?";
        update(sql1,"帅哥","2");
    }


    /**
     * 通用增删改
     * @param sql
     * @param args
     * @return
     */
    public int update(String sql,Object...args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1、获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i <args.length ; i++) {
                ps.setObject(i+1,args[i]);
            }
            //4.执行操作
            //ps.execute();//执行增删改返回false
            return ps.executeUpdate();//返回影响行数
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源关闭
            JDBCUtils.closeResource(conn,ps);
        }
        return 0;
    }

    @Test
    public void test5(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
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
            conn = DriverManager.getConnection(url, user, password);
            // System.out.println(connection);
            //
            String sql="insert into customers(name,email,birth)values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"胡歌");
            ps.setString(2,"ge@qq.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1998-11-02");
            ps.setDate(3,new java.sql.Date(date.getTime()));

            //执行sql
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {

            //7.资源关闭
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



    }

    //修改
    @Test
    public void testUpdata(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1、获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql
            String sql="update customers set name=? where id=?";
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            ps.setObject(1,"乔布洛");
            ps.setObject(2,3);
            //4.执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //5.资源关闭
            JDBCUtils.closeResource(conn,ps);
        }
    }

}
