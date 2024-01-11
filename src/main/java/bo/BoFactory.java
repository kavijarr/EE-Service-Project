package bo;

import bo.impl.ItemBoImpl;
import util.BoType;

public class BoFactory {

    private static BoFactory boFactory;
    private BoFactory(){

    }
    public static BoFactory getInstance(){
        return boFactory!=null ? boFactory : (boFactory=new BoFactory());
    }

    public static <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case ITEM:return (T) new ItemBoImpl();
        }
        return null;
    }

}
