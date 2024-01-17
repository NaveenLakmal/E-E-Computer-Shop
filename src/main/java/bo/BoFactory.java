package bo;

import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.UserBoImpl;
import dao.util.BoType;
import entity.Item;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }
    public static BoFactory getInstance(){
        return boFactory!=null? boFactory:(boFactory=new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER: return (T) new UserBoImpl();
            case ITEM: return (T) new ItemBoImpl();

        }
        return null;
    }
}
