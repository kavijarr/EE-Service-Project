package bo.custom.impl;

import bo.custom.CustomerBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dto.CustomerDto;
import entity.Customer;
import entity.Item;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return customerDao.save(new Customer(
                dto.getId(),
                dto.getCustomerName(),
                dto.getCustomerEmail(),
                dto.getContactNumber()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean deleteCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public List<CustomerDto> getAll() {
        List<CustomerDto> list = new ArrayList<>();
        List<Customer> customers = customerDao.getAll();
        for (Customer entity:customers) {
            list.add(new CustomerDto(
               entity.getId(),
               entity.getCustomerName(),
               entity.getCustomerEmail(),
               entity.getContactNumber()
            ));
        }
        return list;
    }

    @Override
    public String generateId() {
        Customer lastCustomer = customerDao.getLastCustomer();
        if (lastCustomer!=null){
            String id = lastCustomer.getId();
            int num = Integer.parseInt(id.split("[C]")[1]);
            num++;
            return (String.format("C%03d",num));
        }else {
            return ("C001");
        }
    }

    @Override
    public CustomerDto getCustomer(String id) {
        Customer entity = customerDao.getCustomer(id);
        return new CustomerDto(
                entity.getId(),
                entity.getCustomerName(),
                entity.getCustomerEmail(),
                entity.getContactNumber()
        );
    }

    @Override
    public JRBeanCollectionDataSource getCustomerReport() {
        List<Customer> customers = customerDao.getAll();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);
        return dataSource;
    }
}
