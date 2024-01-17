package bo.custom.impl;

import bo.custom.RepairBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.Repairdao;
import dto.OrderDto;
import dto.RepairDto;
import entity.Repair;
import util.DaoType;

public class RepairBoImpl implements RepairBo {

    Repairdao repairdao = DaoFactory.getInstance().getDao(DaoType.REPAIR);
    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public Boolean saveRepair(RepairDto dto) {
        return repairdao.save(new Repair(
                dto.getRepairId(),
                dto.getDate(),
                customerDao.getCustomer(dto.getCustomerId()),
                dto.getItemName()
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
}
