package dao.custom;

import dao.CrudDao;
import entity.Repair;
import entity.RepairDetails;

import java.util.List;

public interface Repairdao extends CrudDao<Repair> {
    Repair getLastRepair();
    Boolean saveDetails(List<RepairDetails> list);
    Repair getRepair(String id);
}
