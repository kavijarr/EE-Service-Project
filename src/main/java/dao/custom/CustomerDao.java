package dao.custom;

import dao.CrudDao;
import entity.Customer;

import java.util.List;

public interface CustomerDao extends CrudDao<Customer> {
    Customer getLastCustomer();
    Customer getCustomer(String id);
}
