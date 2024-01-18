package bo.custom;

import bo.SuperBo;
import dto.RepairDetailsDto;
import dto.RepairDto;
import entity.Repair;
import util.StatusType;

import java.util.List;

public interface RepairBo extends SuperBo {

    Boolean saveRepair(RepairDto dto);
    String generateId();
    List<RepairDto> getAll();
    Boolean saveDetails(List<RepairDetailsDto> list);

    boolean updateStatus(StatusType type, String repairId);
    RepairDto getRepair(String id);
}
