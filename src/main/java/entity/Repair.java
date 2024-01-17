package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Repair {
    @Id
    private String repairId;

    private String date;

    private String itemName;

    @ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;

    @OneToMany(mappedBy = "repair", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<RepairDetails> list;

    public Repair(String repairId, String date, Customer customer, String itemName) {
        this.repairId = repairId;
        this.date = date;
        this.customer = customer;
        this.itemName= itemName;
    }
}
