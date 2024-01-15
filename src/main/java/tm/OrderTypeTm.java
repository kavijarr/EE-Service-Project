package tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderTypeTm extends RecursiveTreeObject<OrderTypeTm> {
    private String id;
    private String date;
    private String status;
    private JFXButton btn;
}
