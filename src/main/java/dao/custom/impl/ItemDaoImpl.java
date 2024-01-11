package dao.custom.impl;

import dao.custom.ItemDao;
import entity.Item;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public Boolean save(Item entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean update(Item entity) {
        return null;
    }

    @Override
    public Boolean delete(Item entity) {
        return null;
    }

    @Override
    public List getAll(Item entity) {
        return null;
    }

    @Override
    public Item getLastItem() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Item ORDER BY id DESC");
        query.setMaxResults(1);
        List list = query.list();
        if (!list.isEmpty()){
            Item entity = (Item) list.get(0);
            return entity;
        }
        session.close();
        return null;
    }
}
