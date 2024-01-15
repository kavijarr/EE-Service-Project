package bo.custom;

import bo.SuperBo;
import dto.ItemDto;

public interface OrderDetailsBo extends SuperBo {
    Boolean updateDetails(ItemDto dto);
}
