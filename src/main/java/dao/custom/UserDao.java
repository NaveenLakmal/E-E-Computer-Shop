package dao.custom;

import dao.CrudDao;
import entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User> {

    String loginCheckDao(User entity) throws SQLException, ClassNotFoundException;

}
