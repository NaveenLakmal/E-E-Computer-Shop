package dao.custom;

import dao.CrudDao;
import dto.CustomerDto;
import dto.OrderDto;
import entity.Customer;

import java.sql.SQLException;

public interface CustomerDao extends CrudDao<Customer> {
    CustomerDto getLastCustomer() throws SQLException, ClassNotFoundException;
}
