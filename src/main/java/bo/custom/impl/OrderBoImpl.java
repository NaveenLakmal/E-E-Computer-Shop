package bo.custom.impl;

import bo.custom.OrderBo;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        //try {
           // OrderDto dto = orderDao.getLastOrder();
           // if (dto!=null){
                //String id = dto.getOrderId();
                //int num = Integer.parseInt(id.split("[D]")[1]);
                //num++;
               // return String.format("D%03d",num);
           // }else{
                return "D001";
           // }
       // } catch (SQLException e) {
          //  throw new RuntimeException(e);
        //} catch (ClassNotFoundException e) {
        //    throw new RuntimeException(e);
        //}
    }

    @Override
    public List<OrderDto> allOrder() throws SQLException, ClassNotFoundException {
        return null;
    }
}
