package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import entity.Item;

import java.util.List;

public interface ItemBo extends SuperBo {
     Boolean saveItem(ItemDto dto);
     String generateID();
     List<ItemDto> getAll();
     ItemDto getItem(String id);
     Boolean updateItem(ItemDto dto);
}
