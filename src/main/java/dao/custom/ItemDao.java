package dao.custom;

import dao.CrudDao;
import entity.Item;

public interface ItemDao extends CrudDao<Item> {
    Item getLastItem();
    Item getItem(String id);
}
