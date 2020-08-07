package main.java.com.czl.dao;
import main.java.com.czl.utils.JDBCUtils;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/5
 * @Description:
 */
public class BaseDao {

    /**
     * 通用增删改2.0(增加事务处理
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public void cdu(Connection conn, String sql, Object...args){
        PreparedStatement ps = null;
        try {
            //1、获取数据库连接
            //conn = JDBCUtils.getConnection();
            //2.预编译sql
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i <args.length ; i++) {
                ps.setObject(i+1,args[i]);
            }
            //4.执行操作
            ps.execute();//执行增删改返回false
           // return ps.executeUpdate();//返回影响行数
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //修改其为自动提交
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //5.资源关闭
            JDBCUtils.closeResource(null,ps);
        }
        //return 0;
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }


    /**
     * 通用查询不同表2.0(增加事务处理)多条记录
     * @param conn
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queries(Connection conn,Class<T> clazz, String sql, Object... args) {
        //Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //conn = JDBCUtils.getConnection();
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
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    /**
     * 通用查询特殊值2.0(增加事务处理)
     * @param conn
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    public <E>E getValue(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            if(rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null,ps,rs);
        }
        return null;
    }

}
