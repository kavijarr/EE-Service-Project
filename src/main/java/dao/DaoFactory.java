package dao;

import bo.impl.ItemBoImpl;
import dao.custom.impl.ItemDaoImpl;
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
        }
        return null;
    }
}
