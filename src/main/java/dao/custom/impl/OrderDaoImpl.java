package dao.custom.impl;

import dao.custom.OrderDao;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailDto;
import dto.OrderDto;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(OrderDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Orders order = new Orders(
                dto.getOrderId(),
                dto.getDate(),
                dto.getCategory(),
                dto.getDescription(),
                dto.getSubCategory()
        );
        order.setCustomer(session.find(Customer.class,dto.getCustId()));
        session.save(order);

        List<OrderDetailDto> list = dto.getList(); //dto type

        for (OrderDetailDto detailDto:list) {
            OrderDetail orderDetail = new OrderDetail(
                    new OrderDetailsKey(detailDto.getOrderId(), detailDto.getItemCode()),
                    session.find(AdditionalItem.class, detailDto.getItemCode()),
                    order,
                    detailDto.getQty(),
                    detailDto.getPrice()


            );
            session.save(orderDetail);
        }

        transaction.commit();
        session.close();
        return true;
    }


    @Override
    // Existing imports and class declaration...

    public boolean update(OrderDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Orders orderToUpdate = session.find(Orders.class, dto.getOrderId());

            if (orderToUpdate != null) {
                List<OrderDetailDto> list = dto.getList();

                for (OrderDetailDto detailDto : list) {
                    AdditionalItem item = session.find(AdditionalItem.class, detailDto.getItemCode());

                    OrderDetail existingOrderDetail = orderToUpdate.getOrderDetails().stream()
                            .filter(od -> od.getId().getItemCode().equals(detailDto.getItemCode()))
                            .findFirst()
                            .orElse(null);

                    if (existingOrderDetail == null) {
                        OrderDetail newOrderDetail = new OrderDetail(
                                new OrderDetailsKey(dto.getOrderId(), detailDto.getItemCode()),
                                item,
                                orderToUpdate,
                                detailDto.getQty(),
                                detailDto.getPrice()
                        );
                        session.save(newOrderDetail);
                    } else {
                        existingOrderDetail.setQty(detailDto.getQty());
                        existingOrderDetail.setPrice(detailDto.getPrice());
                    }
                }

                //orderToUpdate.setDate(dto.getDate());
                //orderToUpdate.setCategory(dto.getCategory());
                orderToUpdate.setDescription(dto.getDescription());
                orderToUpdate.setSubCategory(dto.getSubCategory());
                //orderToUpdate.setCustomer(session.find(Customer.class, dto.getCustId()));

                session.update(orderToUpdate);

                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } finally {
            session.close();
        }
    }





    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDto> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Orders");
        List<Orders> ordersList = query.list();
        List<OrderDto> orderDtoList=new ArrayList<>();

        for (Orders orders:ordersList) {
            orderDtoList.add(new OrderDto(
                    orders.getOrderId(),
                    orders.getDate(),
                    orders.getSubCategory(),
                    orders.getDescription()

            ));

        }

        session.close();
        //System.out.println(list);
        return orderDtoList;
    }

    @Override
    public OrderDto getLastOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    null
            );
        }
        return null;
    }
}
