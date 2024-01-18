package tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RepairTm extends RecursiveTreeObject<RepairTm> {
    private String id;
    private String date;
    private String status;
    private JFXButton btn;
}
