package bo.custom;

import bo.SuperBo;
import dto.ItemDto;

import java.sql.SQLException;

public interface ItemBo extends SuperBo {
    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;
}
