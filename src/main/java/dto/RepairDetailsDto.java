package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RepairDetailsDto {
    private long id;
    private String partName;
    private double price;
    private String repairId;
}
