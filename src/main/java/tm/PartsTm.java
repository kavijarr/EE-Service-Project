package tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartsTm extends RecursiveTreeObject<PartsTm> {
    private String partName;
    private double partCost;
    private JFXButton btn;
}
