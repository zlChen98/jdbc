package main.java.czl.dao;

import main.java.czl.bean.Customer;
import main.java.czl.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SocketHandler;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/5
 * @Description:
 */
public class BaseDao {
    /**
     * 通用增删改不同表2.0(增加事务处理)
     * @param conn
     * @param sql
     * @param args
     */
    public void cdu(Connection conn, String sql, Object...args){
        try {
            QueryRunner queryRunner = new QueryRunner();
            int i = queryRunner.update(conn, sql, args);
            System.out.println("影响行数为"+i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null);
        }
    }

    /**
     * 通用查询不同表2.0(增加事务处理)
     * @param conn
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T query(Connection conn,Class<T> clazz, String sql, Object... args) {
        T t = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            BeanHandler<T> beanHandler = new BeanHandler<T>(clazz);
            t = queryRunner.query(conn, sql, beanHandler, args);
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null);
        }
        return null;
    }


    /**
     * 通用查询不同表2.0(增加事务处理)多条记录
     */
    public <T> List<T> queries(Connection conn, Class<T> clazz, String sql, Object... args){
        try {
            QueryRunner queryRunner = new QueryRunner();
            BeanListHandler<T> beanListHandler = new BeanListHandler<T>(clazz);
            List<T> list = queryRunner.query(conn, sql, beanListHandler, args);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null);
        }
        return null;
    }

    /**
     * 通用查询特殊值2.0(增加事务处理)
     * @return
     */
    public <E>E getValue(Connection conn, String sql,Object...args)  {
        try {
            QueryRunner queryRunner = new QueryRunner();
            ScalarHandler objectScalarHandler = new ScalarHandler();
            return (E)queryRunner.query(conn, sql, objectScalarHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null);
        }
        return null;
    }

}
