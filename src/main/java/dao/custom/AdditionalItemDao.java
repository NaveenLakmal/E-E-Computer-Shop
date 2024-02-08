package dao.custom;

import dao.CrudDao;
import dto.AdditionalItemDto;
import dto.CustomerDto;
import entity.AdditionalItem;

import java.sql.SQLException;

public interface AdditionalItemDao extends CrudDao<AdditionalItem> {
    AdditionalItemDto getLastItem() throws SQLException, ClassNotFoundException;
}
