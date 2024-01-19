package bo.custom;

import bo.SuperBo;
import dto.OrderDto;
import entity.Orders;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import tm.OrderTm;

import java.util.List;

public interface OrderBo extends SuperBo {
    void setTmList(ObservableList<OrderTm> tmList);
    ObservableList<OrderTm> getTmList();
    String generateId();
    boolean saveOrder(OrderDto dto);
    List<OrderDto> getAll();
    JRBeanCollectionDataSource getOrderSummery(String value);
}
