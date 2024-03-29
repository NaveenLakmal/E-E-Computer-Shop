package bo.custom.impl;

import bo.custom.OrderBo;
import dao.custom.OrderDao;
import dao.custom.impl.OrderDaoImpl;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao = new OrderDaoImpl();
    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDao.save(dto);
    }

    @Override
    public boolean updateOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDao.update(dto);
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            OrderDto dto = orderDao.getLastOrder();
            if (dto!=null){
                String id = dto.getOrderId();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                return String.format("D%03d",num);
            }else{
                return "D001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStatus(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDao.updateStatus(dto);
    }

    @Override
    public List<OrderDto> allOrder() throws SQLException, ClassNotFoundException {
        List<OrderDto> entityList = orderDao.getAll();
        return entityList;
    }
}
