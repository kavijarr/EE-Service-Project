package bo.impl;

import bo.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dto.ItemDto;
import entity.Item;
import util.DaoType;

public class ItemBoImpl implements ItemBo {

    ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public Boolean saveItem(ItemDto dto) {
        return itemDao.save(new Item(
                dto.getId(),
                dto.getName(),
                //dto.getCategory(),
                dto.getQtyOnHand(),
                dto.getUnitPrice(),
                dto.getImgUrl())
        );
    }

    @Override
    public String generateID() {
        Item lastItem = itemDao.getLastItem();
        if (lastItem!=null){
            String id = lastItem.getId();
            int num = Integer.parseInt(id.split("[P]")[1]);
            num++;
            return (String.format("P%03d",num));
        }else {
            return ("P001");
        }
    }
}
