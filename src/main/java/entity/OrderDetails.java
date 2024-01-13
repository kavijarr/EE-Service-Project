package entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class OrderDetails {
    @EmbeddedId
    private OrderDetailsKey id;


    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "itemId")
    Item item;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    Orders order;

    private int qty;
    private double unitPrice;
}
