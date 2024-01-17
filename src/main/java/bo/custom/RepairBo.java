package bo.custom;

import bo.SuperBo;
import dto.RepairDto;
import entity.Repair;

public interface RepairBo extends SuperBo {

    Boolean saveRepair(RepairDto dto);
    String generateId();
}
