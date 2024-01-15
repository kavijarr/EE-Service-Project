package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dto.ItemDto;
import dto.OrderDto;
import entity.Item;
import entity.OrderDetails;
import util.DaoType;

public class OrderDetailsBoImpl implements OrderDetailsBo {

    private OrderDto dto;

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

    public OrderDto getDto() {
        return dto;
    }

    public void setDto(OrderDto dto) {
        this.dto = dto;
    }
}
