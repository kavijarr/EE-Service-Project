package dao.custom.impl;

import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.custom.OrderDao;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.DaoType;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public OrderDto getLastOrder() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Orders ORDER BY orderId DESC");
        query.setMaxResults(1);
        List list = query.list();
        if (!list.isEmpty()){
            Orders entity = (Orders) list.get(0);
            return new OrderDto(
                    entity.getOrderId(),
                    entity.getDate(),
                    entity.getCustomer().getId(),
                    null
            );
        }
        session.close();
        return null;
    }

    @Override
    public List<OrderDetailsDto> getOrderDetails(String id) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM OrderDetails WHERE orderId = :findId");
        query.setParameter("findId",id);
        List<OrderDetails> list = query.list();
        List<OrderDetailsDto> dtoList = new ArrayList<>();
        for (OrderDetails entity:list) {
            dtoList.add(new OrderDetailsDto(
                    entity.getOrder().getOrderId(),
                    entity.getItem().getId(),
                    entity.getQty(),
                    entity.getUnitPrice()
            ));
        }
        session.close();
        return dtoList;
    }

    @Override
    public Boolean save(OrderDto dto) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Orders order = new Orders(
                dto.getId(),
                dto.getDate()
        );
        order.setCustomer(session.find(Customer.class,dto.getCustomerId()));
        session.save(order);

        List<OrderDetailsDto> list = dto.getList();
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetailsDto orderDetailsDto: list) {
            OrderDetails orderDetails = new  OrderDetails(
                    new OrderDetailsKey(orderDetailsDto.getOrderId(),orderDetailsDto.getItemId()),
                    session.find(Item.class,orderDetailsDto.getItemId()),
                    order,
                    orderDetailsDto.getQty(),
                    orderDetailsDto.getUnitPrice()
            );
            Item item = session.find(Item.class,orderDetailsDto.getItemId());
            item.setQtyOnHand(item.getQtyOnHand()- orderDetailsDto.getQty());
            itemDao.update(item);
            session.save(orderDetails);
            orderDetailsList.add(orderDetails);
        }

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Boolean update(OrderDto dto) {
        return null;
    }

    @Override
    public Boolean delete(String value) {
        return null;
    }

    @Override
    public List<OrderDto> getAll() {
        List<OrderDto> dtoList = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Orders");
        List<Orders> list = query.list();
        for (Orders entity:list) {
            dtoList.add(new OrderDto(
                    entity.getOrderId(),
                    entity.getDate(),
                    entity.getCustomer().getId(),
                    getOrderDetails(entity.getOrderId())
            ));
        }
        return dtoList;
    }


}
