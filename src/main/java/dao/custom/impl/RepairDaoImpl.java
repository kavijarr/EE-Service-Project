package dao.custom.impl;

import dao.custom.Repairdao;
import entity.Item;
import entity.Repair;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class RepairDaoImpl implements Repairdao {
    @Override
    public Boolean save(Repair entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean update(Repair entity) {
        return null;
    }

    @Override
    public Boolean delete(String value) {
        return null;
    }

    @Override
    public List<Repair> getAll() {
        return null;
    }

    @Override
    public Repair getLastRepair() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Repair ORDER BY repairId DESC");
        query.setMaxResults(1);
        List list = query.list();
        if (!list.isEmpty()){
            Repair entity = (Repair) list.get(0);
            return entity;
        }
        session.close();
        return null;
    }
}
