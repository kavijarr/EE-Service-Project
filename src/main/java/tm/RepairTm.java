package tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Label;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RepairTm extends RecursiveTreeObject<RepairTm> {
    private String id;
    private String date;
    private Label status;
    private JFXButton btn;
}
