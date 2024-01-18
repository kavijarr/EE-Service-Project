package bo.custom.impl;

import bo.custom.RepairBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.Repairdao;
import dto.RepairDetailsDto;
import dto.RepairDto;
import entity.Repair;
import entity.RepairDetails;
import util.DaoType;
import util.StatusInfo;
import util.StatusType;

import java.util.ArrayList;
import java.util.List;

public class RepairBoImpl implements RepairBo {

    Repairdao repairdao = DaoFactory.getInstance().getDao(DaoType.REPAIR);
    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public Boolean saveRepair(RepairDto dto) {
        return repairdao.save(new Repair(
                dto.getRepairId(),
                dto.getDate(),
                dto.getItemName(),
                dto.getDesc(),
                StatusInfo.statusType(StatusType.PENDING),
                customerDao.getCustomer(dto.getCustomerId())
        ));
    }

    @Override
    public String generateId() {
        Repair lastItem = repairdao.getLastRepair();
        if (lastItem!=null){
            String id = lastItem.getRepairId();
            int num = Integer.parseInt(id.split("[R]")[1]);
            num++;
            return (String.format("SR%03d",num));
        }else {
            return ("SR001");
        }
    }

    @Override
    public List<RepairDto> getAll() {
        List<Repair> list = repairdao.getAll();
        List<RepairDto> dtoList = new ArrayList<>();
        for (Repair entity:list) {
            dtoList.add(new RepairDto(
                    entity.getRepairId(),
                    entity.getDate(),
                    entity.getCustomer().getId(),
                    entity.getItemName(),
                    entity.getDescription(),
                    entity.getStatus()
            ));
        }
        return dtoList;
    }

    @Override
    public Boolean saveDetails(List<RepairDetailsDto> list) {
        List<RepairDetails> entityList = new ArrayList<>();
        for (RepairDetailsDto dto: list) {
            entityList.add(new RepairDetails(
                    dto.getPartName(),
                    dto.getPrice(),
                    repairdao.getRepair(dto.getRepairId())
            ));
        }
        return repairdao.saveDetails(entityList);
    }

    @Override
    public boolean updateStatus(StatusType type, String repairId) {
        return repairdao.updateStatus(type, repairId);
    }

    @Override
    public RepairDto getRepair(String id) {
        Repair repair = repairdao.getRepair(id);
        List<RepairDetails> list = repair.getList();
        List<RepairDetailsDto> dtoList=new ArrayList<>();
        for (RepairDetails entity: list) {
            dtoList.add(new RepairDetailsDto(
                    entity.getPartName(),
                    entity.getPrice(),
                    entity.getRepair().getRepairId()
            ));
        }
        RepairDto repairDto = new RepairDto(
                repair.getRepairId(),
                repair.getDate(),
                repair.getCustomer().getId(),
                repair.getItemName(),
                repair.getDescription(),
                repair.getStatus()
        );
        repairDto.setList(dtoList);
        return repairDto;
    }

}
