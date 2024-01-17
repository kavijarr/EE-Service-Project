package entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class RepairDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String partName;
    private double price;

    @ManyToOne
    @JoinColumn(name = "repairId")
    Repair repair;

    public RepairDetails(String partName, double price, Repair repair) {
        this.partName = partName;
        this.price = price;
        this.repair = repair;
    }
}
