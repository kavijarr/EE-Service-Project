package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import entity.Item;
import entity.OrderDetails;
import entity.OrderDetailsKey;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public Boolean save(Item entity) {
        return null;
    }

    @Override
    public Boolean update(Item entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        OrderDetails orderDetails = session.find(OrderDetails.class,entity.getId());
        orderDetails.getItem().setId(null);
        session.save(orderDetails);
        transaction.commit();
        return true;
    }

    @Override
    public Boolean delete(String value) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }
}
