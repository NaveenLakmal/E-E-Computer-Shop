package dao;

import dao.custom.impl.*;
import dao.util.DaoType;

public class DaoFactory {

    private static DaoFactory daoFactory;

    private DaoFactory(){

    }
    public static DaoFactory getInstance(){
        return daoFactory!=null? daoFactory:(daoFactory=new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case USER: return(T) new UserDaoImpl();
            case ITEM: return(T) new AdditionalItemDaoImpl();
            case CUSTOMER: return(T) new CustomerDaoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailDaoImpl();
            case ORDER: return (T) new OrderDaoImpl();


        }
        return null;
    }
}
