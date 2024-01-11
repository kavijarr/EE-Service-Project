package bo;

import dto.ItemDto;
import entity.Item;

public interface ItemBo extends SuperBo{
     Boolean saveItem(ItemDto dto);
     String generateID();
}
