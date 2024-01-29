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
    private double price;

}
