package bo.custom;

import bo.SuperBo;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {
    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItems(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    List<ItemDto> allItems() throws SQLException, ClassNotFoundException;
}
