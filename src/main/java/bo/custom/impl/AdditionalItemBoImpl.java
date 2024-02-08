package bo.custom.impl;

import bo.custom.AdditionalItemBo;
import dao.DaoFactory;
import dao.custom.AdditionalItemDao;
import dao.util.DaoType;
import dto.AdditionalItemDto;
import dto.CustomerDto;
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
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            AdditionalItemDto dto = additionalItemDao.getLastItem();
            if (dto!=null){
                String id = dto.getItemCode();
                int num = Integer.parseInt(id.split("[P]")[1]);
                num++;
                return String.format("P%03d",num);
            }else{
                return "P001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
