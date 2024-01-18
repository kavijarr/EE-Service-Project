package dto;

import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class RepairDetailsDto {
    private long id;
    private String partName;
    private double price;
    private String repairId;

    public RepairDetailsDto(String partName, double price, String repairId) {
        this.partName = partName;
        this.price = price;
        this.repairId = repairId;
    }
}
