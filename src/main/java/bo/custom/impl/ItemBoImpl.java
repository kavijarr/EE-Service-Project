package bo.custom.impl;

import bo.custom.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dto.ItemDto;
import entity.Item;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {

    ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public Boolean saveItem(ItemDto dto) {
        return itemDao.save(new Item(
//                dto.getId(),
//                dto.getName(),
//                //dto.getCategory(),
//                dto.getQtyOnHand(),
//                dto.getUnitPrice(),
//                dto.getImgUrl()),
//                dto.getIsDisabled()
                dto.getId(),
                dto.getName(),
                dto.getQtyOnHand(),
                dto.getUnitPrice(),
                dto.getImgUrl(),
                dto.getIsDisabled()
        ));
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

    @Override
    public List<ItemDto> getAll() {
        List<Item> entityList = itemDao.getAll();
        List<ItemDto> dtoList = new ArrayList<>();
        for (Item entity:entityList) {
            dtoList.add(new ItemDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getQtyOnHand(),
                    entity.getUnitPrice(),
                    entity.getImgUrl(),
                    entity.getIsDisabled()
            ));
        }
        return dtoList;
    }

    @Override
    public List<ItemDto> getEnabled() {
        List<Item> entityList = itemDao.getEnabledItems();
        List<ItemDto> dtoList = new ArrayList<>();
        for (Item entity:entityList) {
            dtoList.add(new ItemDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getQtyOnHand(),
                    entity.getUnitPrice(),
                    entity.getImgUrl(),
                    entity.getIsDisabled()
            ));
        }
        return dtoList;
    }

    @Override
    public ItemDto getItem(String id) {
        Item entity = itemDao.getItem(id);
        return new ItemDto(
                entity.getId(),
                entity.getName(),
                entity.getQtyOnHand(),
                entity.getUnitPrice(),
                entity.getImgUrl(),
                entity.getIsDisabled()
        );
    }

    @Override
    public Boolean updateItem(ItemDto dto) {
        Item entity = new Item(
                dto.getId(),
                dto.getName(),
                dto.getQtyOnHand(),
                dto.getUnitPrice(),
                dto.getImgUrl(),
                dto.getIsDisabled()
                );
        return itemDao.update(entity);
    }

    @Override
    public Boolean deleteItem(String value) {
        return itemDao.delete(value);
    }
}
