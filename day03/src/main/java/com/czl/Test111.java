package main.java.com.czl;



import com.alibaba.druid.pool.DruidDataSourceFactory;
import main.java.com.czl.bean.Customer;
import main.java.com.czl.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import javax.sql.DataSource;

import java.io.InputStream;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/5
 * @Description:
 */
public class Test111 {

    @org.junit.Test
    public void Test () throws Exception {

        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("main/resources/druid.properties");
        pros.load(is);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
        Connection conn = dataSource.getConnection();
        System.out.println(conn);

    }

    @Test
    public void testInsert() throws Exception {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection1();
            String sql="insert into customers(name,email,birth)values(?,?,?)";
            int i = runner.update(conn, sql, "hjdsh", "hdieu@dj", "1998-03-04");
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    /**
     * BeanHandler是ResultSethandler接口的实现类，用于封装表中的一条数据
     * @throws Exception
     */
    @Test
    public void testQuery() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection1();
            String sql="select id,name,email,birth from customers where id=?";
            //BeanHandler<Customer> handler = new BeanHandler<Customer>(Customer.class);
            MapHandler mapHandler = new MapHandler();

           // Customer cu = runner.query(conn, sql, handler, 5);
            Map<String, Object> map = runner.query(conn, sql, mapHandler, 5);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @Test
    public void testQuery1() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection1();
            String sql="select id,name,email,birth from customers";
            BeanListHandler<Customer> handlers = new BeanListHandler<>(Customer.class);
            List<Customer> list = runner.query(conn, sql, handlers);
            for (Customer cu:list) {
                System.out.println(cu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }

    }


    @Test
    public void testCount() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection1();
            String sql="select count(*) from customers";
            ScalarHandler handler = new ScalarHandler();
            Long o = (Long) runner.query(conn, sql, handler);

             System.out.println(o);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }

    }


}
