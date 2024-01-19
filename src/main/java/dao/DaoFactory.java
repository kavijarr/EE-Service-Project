package dao;

import dao.custom.impl.*;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){

    }
    public static DaoFactory getInstance(){
        return daoFactory!=null ? daoFactory : (daoFactory=new DaoFactory());
    }

    public static <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case ITEM:return (T) new ItemDaoImpl();
            case CUSTOMER:return (T) new CustomerDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
            case ORDERDETAILS:return (T) new OrderDetailsDaoImpl();
            case REPAIR:return (T) new RepairDaoImpl();
            case USER:return (T) new UserDaoImpl();
        }
        return null;
    }
}
