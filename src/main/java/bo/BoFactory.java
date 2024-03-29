package bo;

import bo.custom.impl.*;
import dao.util.BoType;

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
            case ADDITIONALITEM: return (T) new AdditionalItemBoImpl();
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailBoImpl();
            case ORDER: return (T) new OrderBoImpl();
            case ITEM: return (T) new ItemBoImpl();

        }
        return null;
    }
}
