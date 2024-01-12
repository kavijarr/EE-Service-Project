package tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderTm extends RecursiveTreeObject<OrderTm> {
    private String id;
    private String itemName;
    private int qty;
    private double amount;
    private JFXButton deleteBtn;
}
