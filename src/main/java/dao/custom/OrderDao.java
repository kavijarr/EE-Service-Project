package dao.custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.OrderDto;
import entity.Orders;

public interface OrderDao extends CrudDao<OrderDto> {
    OrderDto getLastOrder();
}
