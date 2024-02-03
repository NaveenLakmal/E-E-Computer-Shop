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

    public OrderDto(String orderId, String date, String subCategory, String description) {
        this.orderId = orderId;
        this.date = date;
        this.subCategory = subCategory;
        this.description = description;
    }

    public OrderDto(String orderId, String custId, String category, String subCategory, String description, List<OrderDetailDto> list) {
        this.orderId = orderId;
        this.custId = custId;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
        this.list = list;
    }


    private List<OrderDetailDto> list;
}
