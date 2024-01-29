package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDto {

    private String itemCode;
    private String category;
    private String subCategory;
    private String description;

}
