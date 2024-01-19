package dao.custom.impl;

import dao.custom.UserDao;
import entity.Item;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public Boolean save(User entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean update(User entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, entity.getId());
        user.setPassword(entity.getPassword());
        session.save(user);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean delete(String value) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, value);
        session.delete(user);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<User> getAll() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("From User");
        List<User> list = query.list();
        session.close();
        return list;
    }

    @Override
    public User getLastUser() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User ORDER BY id DESC");
        query.setMaxResults(1);
        List list = query.list();
        if (!list.isEmpty()){
            User entity = (User) list.get(0);
            return entity;
        }
        session.close();
        return null;
    }

    @Override
    public User getUser(String value) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User WHERE email = :search");
        query.setParameter("search",value);
        query.setMaxResults(1);
        List<User> list = query.list();
        session.close();
        if (!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
