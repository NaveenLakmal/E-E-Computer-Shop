package bo.custom.impl;

import bo.custom.AdditionalItemBo;
import dao.DaoFactory;
import dao.custom.AdditionalItemDao;
import dao.util.DaoType;
import dto.AdditionalItemDto;
import entity.AdditionalItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdditionalItemBoImpl implements AdditionalItemBo {

    private AdditionalItemDao additionalItemDao = DaoFactory.getInstance().getDao(DaoType.ADDITIONALITEM);

    @Override
    public boolean saveItem(AdditionalItemDto dto) throws SQLException, ClassNotFoundException {
        return additionalItemDao.save(new AdditionalItem(
                dto.getItemCode(),
                dto.getName(),
                dto.getQty(),
                dto.getPrice()
        ));
    }

    @Override
    public boolean updateItems(AdditionalItemDto dto) throws SQLException, ClassNotFoundException {
        return additionalItemDao.update(new AdditionalItem(
                dto.getItemCode(),
                dto.getName(),
                dto.getQty(),
                dto.getPrice()
        ));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return additionalItemDao.delete(id);
    }

    @Override
    public List<AdditionalItemDto> allItems() throws SQLException, ClassNotFoundException {
        List<AdditionalItem> entityList = additionalItemDao.getAll();
        List<AdditionalItemDto> list = new ArrayList<>();
        for (AdditionalItem additionalItem :entityList) {
            list.add(new AdditionalItemDto(
                    additionalItem.getItemCode(),
                    additionalItem.getName(),
                    additionalItem.getQty(),
                    additionalItem.getPrice()
            ));
        }
        return list;
    }
}
