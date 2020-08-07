package main.java.com.czl;




import main.java.com.czl.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @program: jdbc_01
 * @Author: 陈志梁
 * @Data: 2020/8/4
 * @Description: 向goods表中插入20000条数据
 */
public class InsertTest {

    //批量操作1.0
    @Test
    public void test() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql="insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <=20000 ; i++) {
                ps.setObject(1,"name_"+i);
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }

    //批量操作2.0（攒sql）
    @Test
    public void test1() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql="insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <=1000000 ; i++) {
                ps.setObject(1,"AAA"+i);
                //攒sql
                ps.addBatch();
                if (i%500==0){
                    ps.executeBatch();//执行batch
                    ps.clearBatch();//清空batch
                };
            }
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }

    //批量操作3.0（攒sql）不允许自动提交
    @Test
    public void test2() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);//设置不允许自动提交
            String sql="insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <=1000000 ; i++) {
                ps.setObject(1,"AAA"+i);
                //攒sql
                ps.addBatch();
                if (i%500==0){
                    ps.executeBatch();//执行batch
                    ps.clearBatch();//清空batch
                }
            }
            conn.commit();//提交数据
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
    }
}
