package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import entity.Customer;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.List;

public interface CustomerBo extends SuperBo {
    boolean saveCustomer(CustomerDto dto);
    boolean updateCustomer(CustomerDto dto);
    boolean deleteCustomer(CustomerDto dto);
    List<CustomerDto> getAll();
    String generateId();
    CustomerDto getCustomer(String id);
    JRBeanCollectionDataSource getCustomerReport();
}
