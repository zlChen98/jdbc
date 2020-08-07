package main.java.com.czl;

import main.java.com.czl.bean.Customer;
import main.java.com.czl.bean.Order;
import main.java.com.czl.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: jdbc_01
 * @Author: 陈志梁
 * @Data: 2020/8/3
 * @Description: 使用prepaerdStatement实现针对不同表的通用查询操作
 */
public class Query {

    @Test
    public void test() {
        String sql = "select id,name,email,birth from customers where name=?";
        Customer c = getInstance(Customer.class, sql, "帅哥");//Customer.class 获取Class实例对象

        System.out.println(c);

        String sql1="SELECT order_id orderId,order_name orderName,order_date orderDate FROM `order` WHERE order_id=?";
        Order o = getInstance(Order.class, sql1, 1);
        System.out.println(o);
    }

    /**
     *  查询不同表的通用操作
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();//获取结果集的元数据
            int columnCount = rsmd.getColumnCount();//获取结果集列

            if (rs.next()) {
                T t = clazz.newInstance();//newInstance():调用此方法，创建对应的运行时类的对象
                //处理一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);//获取列值
                    String columnLabel = rsmd.getColumnLabel(i + 1);//获取每个列列名
                    //给customer对象指定columnName属性，赋值为columValue  反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }


    @Test
    public void test2() {
        String sql = "select id,name,email,birth from customers where name=?";
        List<Customer> list = getInstances(Customer.class, sql, "帅哥");//Customer.class 获取Class实例对象
        //list.forEach(System.out::println);
        for (Customer customer: list) {
            System.out.println(customer);
        }


        String sql1="SELECT order_id orderId,order_name orderName,order_date orderDate FROM `order` WHERE order_id=?";
        Order o = getInstance(Order.class, sql1, 1);
        System.out.println(o);
    }

    /**
     * 查询不同表的通用操作(多条记录)
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> getInstances(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();//获取结果集的元数据
            int columnCount = rsmd.getColumnCount();//获取结果集列

            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {
                T t = clazz.newInstance();//newInstance():调用此方法，创建对应的运行时类的对象
                //处理一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);//获取列值
                    String columnLabel = rsmd.getColumnLabel(i + 1);//获取每个列列名
                    //给customer对象指定columnName属性，赋值为columValue  反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
                e.printStackTrace();
            } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

}