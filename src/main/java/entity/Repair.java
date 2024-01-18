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
    private String description;
    private int status;

    @ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;

    @OneToMany(mappedBy = "repair", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<RepairDetails> list;

    public Repair(String repairId, String date, String itemName, String description, int status, Customer customer) {
        this.repairId = repairId;
        this.date = date;
        this.itemName = itemName;
        this.description = description;
        this.status=status;
        this.customer = customer;
    }
}
