package main.java.com.czl;

import main.java.com.czl.bean.Customer;
import main.java.com.czl.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @program: jdbc_01
 * @Author: 陈志梁
 * @Data: 2020/8/3
 * @Description: 针对customer表的通用查询
 */
public class CustomerForQuery {


    @Test
    public void test1(){
        String sql="select id,name,email,birth from customers where id=?";
        Customer customer = queryForCustomer(sql, 2);
        System.out.println(customer);
    }

    //通用查询
    public Customer queryForCustomer(String sql, Object...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i <args.length ; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();//获取结果集的元数据
            int columnCount = rsmd.getColumnCount();//获取结果集列

            if (rs.next()){
                Customer customer = new Customer();
                //处理一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {

                    Object columnValue = rs.getObject(i + 1);//获取列值
                    String columnName = rsmd.getColumnName(i+1);//获取每个列列名
                    //给customer对象指定columnName属性，赋值为columValue  反射
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer,columnValue);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }

    //查询
    @Test
    public void test(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql="select id,name,email,birth from customers where id=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,2);
            rs = ps.executeQuery();
            if (rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String email=rs.getString(3);
                Date birth=rs.getDate(4);

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
    }
}
