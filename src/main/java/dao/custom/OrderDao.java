package dao.custom;

import dao.CrudDao;
import dto.OrderDto;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<OrderDto> {
    OrderDto getLastOrder() throws SQLException, ClassNotFoundException;
    boolean updateStatus(OrderDto dto) throws SQLException, ClassNotFoundException;
}
