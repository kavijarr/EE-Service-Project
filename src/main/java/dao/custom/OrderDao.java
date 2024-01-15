package dao.custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.OrderDetails;
import entity.Orders;

import java.util.List;

public interface OrderDao extends CrudDao<OrderDto> {
    OrderDto getLastOrder();

    List<OrderDetailsDto> getOrderDetails(String id);
}
