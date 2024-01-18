package dao.custom.impl;


import dao.custom.UserDao;
import dao.util.HibernateUtil;
import db.DBConnection;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String loginCheckDao(User entity) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE userName = ? AND password= ?");
        pstm.setString(1, entity.getUserName());
        pstm.setString(2, entity.getPassword());
        //pstm.setString(3, entity.getUserType());
//
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            // Return the password if the user is found
            return rst.getString("userType");
        }

        return null; // Return null if no user is found

    }

}
