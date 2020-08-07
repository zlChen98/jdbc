package main.java.czl.dao.impl;

import main.java.czl.bean.Customer;
import main.java.czl.dao.BaseDao;
import main.java.czl.dao.CustomerDao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @program: jdbc
 * @Author: 陈志梁
 * @Data: 2020/8/5
 * @Description:
 */
public class CustomerDaoImpl extends BaseDao implements CustomerDao {
    @Override
    public void insert(Connection conn, Customer customer) {
        String sql="insert into customers(name,email,birth)values(?,?,?)";
        cdu(conn,sql,customer.getName(),customer.getEmail(),customer.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql="delete from customers where id=?";
        cdu(conn,sql,id);
    }

    @Override
    public void update(Connection conn, Customer customer) {
        String sql="update customers set name=?,email=?,birth=? where id=?";
        cdu(conn,sql,customer.getName(),customer.getEmail(),customer.getBirth(),customer.getId());
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql="select id,name,email,birth from customers where id=? ";
        Customer c = query(conn, Customer.class, sql, id);
        return c;
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql="select id,name,email,birth from customers ";
        List<Customer> list = queries(conn, Customer.class, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql="select count(*) from customers";
        return getValue(conn,sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql="select max(birth) from customers";
        return getValue(conn,sql);
    }
}
