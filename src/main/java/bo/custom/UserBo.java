package bo.custom;

import bo.SuperBo;
import dao.util.CrudDao;
import dto.UserDto;

import java.sql.SQLException;

public interface UserBo extends SuperBo {


    boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    String loginCheck(UserDto dto) throws SQLException, ClassNotFoundException;
}
