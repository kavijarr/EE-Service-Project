package dao.custom.impl;

import dao.custom.CustomerDao;
import entity.Customer;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public Boolean save(Customer entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean update(Customer entity) {
        return null;
    }

    @Override
    public Boolean delete(String value) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer");
        List<Customer> customerList = query.list();
        session.close();
        return customerList;
    }

    @Override
    public Customer getLastCustomer() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer ORDER BY id DESC");
        query.setMaxResults(1);
        List list = query.list();
        if (!list.isEmpty()){
            Customer entity = (Customer) list.get(0);
            return entity;
        }
        session.close();
        return null;
    }

    @Override
    public Customer getCustomer(String id) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer WHERE id = :value");
        query.setParameter("value",id);
        query.setMaxResults(1);
        List list = query.list();
        if (!list.isEmpty()){
            Customer customer = (Customer) list.get(0);
            return customer;
        }
        return null;
    }
}
