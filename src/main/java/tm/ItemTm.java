package tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemTm {
    private String id;
    private String itemName;
    private int qty;
    private double unitPrice;
    private JFXButton deleteBtn;
}
