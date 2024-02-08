package dao.custom.impl;

import dao.custom.AdditionalItemDao;
import dao.util.HibernateUtil;
import dto.AdditionalItemDto;
import dto.CustomerDto;
import entity.AdditionalItem;
import entity.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class AdditionalItemDaoImpl implements AdditionalItemDao {


    @Override
    public boolean save(AdditionalItem entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(AdditionalItem entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        AdditionalItem additionalItem = session.find(AdditionalItem.class, entity.getItemCode());
        additionalItem.setName(entity.getName());
        additionalItem.setPrice(entity.getPrice());

        session.save(additionalItem);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(AdditionalItem.class,value));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<AdditionalItem> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM AdditionalItem");
        List<AdditionalItem> list = query.list();

        /*List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
//
//        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            list.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }*/
        session.close();
        return list;
    }

    @Override
    public AdditionalItemDto getLastItem() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // HQL query to get the last customer
            String hql = "FROM AdditionalItem ORDER BY itemCode DESC";
            AdditionalItem lastItem = (AdditionalItem) session.createQuery(hql)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastItem != null) {
                // Convert Hibernate entity to DTO (if needed)
                return new AdditionalItemDto(
                        lastItem.getItemCode(),
                        lastItem.getName(),
                        lastItem.getQty(),
                        lastItem.getPrice()
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
    }
}
