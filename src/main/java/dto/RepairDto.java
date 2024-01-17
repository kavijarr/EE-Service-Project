package dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RepairDto {
    private String repairId;
    private String date;
    private String customerId;
    private String itemName;
    private List<RepairDetailsDto> list;

    public RepairDto(String repairId, String date, String customerId, String itemName) {
        this.repairId = repairId;
        this.date = date;
        this.customerId = customerId;
        this.itemName = itemName;
    }
}
