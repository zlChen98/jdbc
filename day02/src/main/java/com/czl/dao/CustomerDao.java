package main.java.com.czl.dao;

import main.java.com.czl.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/5
 * @Description:
 */
public interface CustomerDao {
    /**
     * 将customer对象添加到数据库中
     * @param conn
     * @param customer
     */
    void insert(Connection conn, Customer customer);

    /**
     * 针对指定id删除记录
     * @param conn
     * @param id
     */
    void deleteById(Connection conn,int id);

    /**
     * 针对内存中的customer对象，去修改某条记录
     * @param conn
     * @param customer
     */
    void update(Connection conn,Customer customer);

    /**
     * 针对指定id，查询对应Customer对象
     * @param conn
     * @param id
     */
    Customer  getCustomerById(Connection conn,int id);

    /**
     * 查询表中所有记录构成的集合
     * @param conn
     * @return
     */
    List<Customer> getAll(Connection conn);

    /**
     * 返回数据表中数据的条目数
     * @param conn
     * @return
     */
    Long getCount(Connection conn);
    /**
     * 返回数据表中最大生日
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);
}
