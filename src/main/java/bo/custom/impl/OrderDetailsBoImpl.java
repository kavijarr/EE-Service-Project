package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dto.ItemDto;
import entity.Item;
import entity.OrderDetails;
import util.DaoType;

public class OrderDetailsBoImpl implements OrderDetailsBo {

    OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDERDETAILS);
    @Override
    public Boolean updateDetails(ItemDto dto) {
        return orderDetailsDao.update(new Item(
                null,
                dto.getName(),
                dto.getQtyOnHand(),
                dto.getUnitPrice(),
                dto.getImgUrl(),
                dto.getIsDisabled()
        ));
    }
}
