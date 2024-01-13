package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class OrderDetailsKey implements Serializable {

    @Column(name = "orderId")
    private String orderId;

    @Column(name = "itemId")
    private String itemId;
}
