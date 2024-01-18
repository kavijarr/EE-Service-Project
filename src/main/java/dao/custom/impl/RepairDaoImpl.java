package dao.custom.impl;

import dao.custom.Repairdao;
import entity.Item;
import entity.Repair;
import entity.RepairDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.StatusInfo;
import util.StatusType;

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
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Repair repair = session.find(Repair.class, entity.getRepairId());
        repair.setStatus(entity.getStatus());
        session.save(repair);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean delete(String value) {
        return null;
    }

    @Override
    public List<Repair> getAll() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Repair");
        List<Repair> list = query.list();
        return list;
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

    @Override
    public Boolean saveDetails(List<RepairDetails> list) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Repair repair = session.find(Repair.class,list.get(0).getRepair().getRepairId());
        for (RepairDetails entity:list) {
            repair.getList().add(entity);
        }
        session.save(repair);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Repair getRepair(String id) {
        Session session = HibernateUtil.getSession();
        Repair repair = session.find(Repair.class, id);
        session.close();
        return repair;
    }

    @Override
    public Boolean updateStatus(StatusType type, String repairId) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Repair repair = session.find(Repair.class, repairId);
        repair.setStatus(StatusInfo.statusType(type));
        session.save(repair);
        transaction.commit();
        session.close();
        return true;
    }
}
