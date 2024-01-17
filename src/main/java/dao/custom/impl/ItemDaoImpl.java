package dao.custom.impl;

import dao.custom.ItemDao;
import dao.util.HibernateUtil;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {


    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }
}
