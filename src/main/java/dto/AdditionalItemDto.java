package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdditionalItemDto {

    private String itemCode;
    private String name;
    private int qty;
    private double price;

}
