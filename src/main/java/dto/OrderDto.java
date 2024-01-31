package dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderDto {
    private String orderId;
    private String date;
    private String custId;

    private String category;
    private String subCategory;
    private String description;

    public OrderDto(String orderId, String date, String custId,String category,String subCategory,String description) {
        this.orderId = orderId;
        this.date = date;
        this.custId = custId;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;

    }

    private List<OrderDetailDto> list;
}
