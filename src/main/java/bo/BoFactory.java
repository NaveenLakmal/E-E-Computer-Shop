package bo;

import bo.custom.impl.*;
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
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailBoImpl();
            case ORDER: return (T) new OrderBoImpl();

        }
        return null;
    }
}
