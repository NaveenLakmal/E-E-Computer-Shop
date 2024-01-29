package dao.custom.impl;

import dao.custom.AdditionalItemDao;
import dao.util.HibernateUtil;
import entity.AdditionalItem;
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
}
