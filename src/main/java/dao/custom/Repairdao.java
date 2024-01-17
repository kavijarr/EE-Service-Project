package dao.custom;

import dao.CrudDao;
import entity.Repair;

public interface Repairdao extends CrudDao<Repair> {
    Repair getLastRepair();
}
