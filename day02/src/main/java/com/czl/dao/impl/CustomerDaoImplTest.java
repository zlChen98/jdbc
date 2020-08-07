package main.java.com.czl.dao.impl;

import main.java.com.czl.bean.Customer;
import main.java.com.czl.utils.JDBCUtils;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/5
 * @Description:
 */
class CustomerDaoImplTest {
    CustomerDaoImpl dao =new CustomerDaoImpl();

    @org.junit.jupiter.api.Test
    void insert(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer hh = new Customer(1, "哇", "sd@ss", new Date(122543138746L));
            dao.insert(conn,hh);
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void deleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //Customer hh = new Customer(1, "hh", "ss@ss", new Date(134543138746L));
            dao.deleteById(conn,4);
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void update() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer hh = new Customer(5, "HHsss", "ss@ss", new Date(134543138746L));
            dao.update(conn,hh);
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //Customer hh = new Customer(5, "HHsss", "ss@ss", new Date(134543138746L));
            Customer cust = dao.getCustomerById(conn, 5);
            System.out.println(cust);
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //Customer hh = new Customer(5, "HHsss", "ss@ss", new Date(134543138746L));
            List<Customer> list = dao.getAll(conn);
            for (Customer c:list) {
                System.out.println(c);
            }
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //Customer hh = new Customer(5, "HHsss", "ss@ss", new Date(134543138746L));
            Long count = dao.getCount(conn);
            System.out.println(count);

            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //Customer hh = new Customer(5, "HHsss", "ss@ss", new Date(134543138746L));
            Date maxBirth = dao.getMaxBirth(conn);
            System.out.println(maxBirth);
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }
}