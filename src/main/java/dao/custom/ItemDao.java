package dao.custom;

import dao.CrudDao;
import entity.Item;

import java.util.List;

public interface ItemDao extends CrudDao<Item> {
    Item getLastItem();
    Item getItem(String id);
    List<Item> getEnabledItems();
}
