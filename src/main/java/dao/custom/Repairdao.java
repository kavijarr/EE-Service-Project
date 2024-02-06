package dao.custom;

import dao.CrudDao;
import dto.RepairDetailsDto;
import entity.Repair;
import entity.RepairDetails;
import util.StatusType;

import java.util.List;

public interface Repairdao extends CrudDao<Repair> {
    Repair getLastRepair();
    Boolean saveDetails(List<RepairDetails> list);
    Repair getRepair(String id);
    Boolean updateStatus(StatusType type, String repairId);

    List<RepairDetailsDto> getDetails(String value);
}
