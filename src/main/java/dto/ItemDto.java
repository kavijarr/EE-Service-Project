package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemDto {
    private String id;
    private String name;
    //private String category;
    private int qtyOnHand;
    private double unitPrice;
    private String imgUrl;
    private Boolean isDisabled;
}
