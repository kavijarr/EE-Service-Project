package bo.custom.impl;

import bo.custom.OrderBo;
import dao.DaoFactory;
import dao.custom.OrderDao;
import dto.OrderDto;
import entity.Item;
import entity.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tm.OrderTm;
import util.DaoType;


public class OrderBoImpl implements OrderBo {
    private static ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
    OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    public ObservableList<OrderTm> getTmList() {
        return tmList;
    }

    @Override
    public String generateId() {
        OrderDto lastItem = orderDao.getLastOrder();
        if (lastItem!=null){
            String id = lastItem.getId();
            int num = Integer.parseInt(id.split("[R]")[1]);
            num++;
            return (String.format("OR%03d",num));
        }else {
            return ("OR001");
        }
    }

    @Override
    public boolean saveOrder(OrderDto dto) {
        return orderDao.save(dto);
    }

    public void setTmList(ObservableList<OrderTm> tmList) {
        this.tmList = tmList;
    }
}
