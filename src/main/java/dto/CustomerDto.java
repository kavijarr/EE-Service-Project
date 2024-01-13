package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDto {
    private String id;
    private String customerName;
    private String customerEmail;
    private String contactNumber;
}
