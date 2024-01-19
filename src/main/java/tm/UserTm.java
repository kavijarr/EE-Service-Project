package tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserTm extends RecursiveTreeObject<UserTm> {
    private String id;
    private String name;
    private String email;
    private JFXButton btn;
}
