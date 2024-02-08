package dao.custom.impl;

import dao.custom.CustomerDao;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.CustomerDto;
import dto.OrderDto;
import entity.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        Customer customer = session.find(Customer.class, entity.getCustId());
        customer.setCustName(entity.getCustName());
        customer.setNumber(entity.getNumber());
        customer.setEmail(entity.getEmail());
        session.save(customer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Customer.class, value));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.list();
        session.close();
        return list;
    }

    @Override
    public CustomerDto getLastCustomer() throws SQLException, ClassNotFoundException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // HQL query to get the last customer
            String hql = "FROM Customer ORDER BY custId DESC";
            Customer lastCustomer = (Customer) session.createQuery(hql)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastCustomer != null) {
                // Convert Hibernate entity to DTO (if needed)
                return new CustomerDto(
                        lastCustomer.getCustId(),
                        lastCustomer.getCustName(),
                        lastCustomer.getEmail(),
                        lastCustomer.getNumber()
                );
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;



//                String sql = "SELECT * FROM customer ORDER BY custId DESC LIMIT 1";
//        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
//        ResultSet resultSet = pstm.executeQuery();
//        if (resultSet.next()){
//            return new CustomerDto(
//                    resultSet.getString(1),
//                    resultSet.getString(2),
//                    resultSet.getString(3),
//                    resultSet.getString(4)
//            );
//        }
//        return null;
    }

}



//    }

