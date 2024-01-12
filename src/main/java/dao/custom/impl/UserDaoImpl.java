package dao.custom.impl;


import dao.custom.UserDao;
import dao.util.HibernateUtil;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }
}
