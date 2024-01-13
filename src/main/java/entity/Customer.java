package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer {
    @Id
    private String id;
    private String customerName;
    private String customerEmail;
    private String contactNumber;

    @OneToMany(mappedBy = "customer")
    private List<Orders> ordersList = new ArrayList<>();

    public Customer(String id, String customerName, String customerEmail, String contactNumber) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.contactNumber = contactNumber;
    }
}
