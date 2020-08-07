package main.java.com.czl;

import main.java.com.czl.bean.Order;
import main.java.com.czl.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @program: jdbc_01
 * @Author: 陈志梁
 * @Data: 2020/8/3
 * @Description: 对Order表的通用查询操作
 */
public class OrderForQuery {
    //通用
    @Test
    public void test1(){
        String sql="SELECT order_id orderId,order_name orderName,order_date orderDate FROM `order` WHERE order_id=?";
        Order order = queryForOrder(sql, 1);
        System.out.println(order);
    }
    //通用查询
    public Order queryForOrder(String sql, Object...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            ResultSetMetaData rsmd = ps.getMetaData();
            int columnCount = rsmd.getColumnCount();

            rs = ps.executeQuery();
            if (rs.next()){
                Order order = new Order();
                for (int i = 0; i <columnCount ; i++) {
                    Object columnValue= rs.getObject(i + 1);
                    //String columnName = rsmd.getColumnName(i + 1);//获取列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);//获取列的别名


                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order,columnValue);
                }
               return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }

    @Test
    public void test(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql="SELECT order_id,order_name,order_date FROM `order` WHERE order_id=?";
            ps = conn.prepareStatement(sql);

            ps.setObject(1,1);
            rs = ps.executeQuery();
            if (rs.next()){
                int id= (int) rs.getObject(1);
                String name = (String) rs.getObject(2);
                Date date = (Date) rs.getObject(3);

                Order order = new Order(id, name, date);
                System.out.println(order);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }


    }
}
