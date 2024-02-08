package bo.custom;

import bo.SuperBo;
import dto.AdditionalItemDto;

import java.sql.SQLException;
import java.util.List;

public interface AdditionalItemBo extends SuperBo {
    boolean saveItem(AdditionalItemDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItems(AdditionalItemDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
    List<AdditionalItemDto> allItems() throws SQLException, ClassNotFoundException;
}
