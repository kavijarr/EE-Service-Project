package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @Id
    private String id;
    private String name;
    //private String category;
    private int qtyOnHand;
    private double unitPrice;
    private String imgUrl;
    private Boolean isDisabled;

    @OneToMany(mappedBy = "item")
    List<OrderDetails> orderDetailsList = new ArrayList<>();

//    public Item(String id, String name, int qtyOnHand, double unitPrice, String imgUrl,Boolean isDisabled) {
//        this.id = id;
//        this.name = name;
//        this.qtyOnHand = qtyOnHand;
//        this.unitPrice = unitPrice;
//        this.imgUrl = imgUrl;
//        this.isDisabled = isDisabled;
//    }


    public Item(String id, String name, int qtyOnHand, double unitPrice, String imgUrl, Boolean isDisabled) {
        this.id = id;
        this.name = name;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.imgUrl = imgUrl;
        this.isDisabled = isDisabled;
    }
}
