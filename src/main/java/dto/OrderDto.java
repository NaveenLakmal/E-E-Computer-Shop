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
    private Double total;
    private String status;

    public OrderDto(String orderId, String date, String subCategory, String description,Double total,String status) {
        this.orderId = orderId;
        this.date = date;
        this.subCategory = subCategory;
        this.description = description;
        this.total=total;
        this.status=status;
    }

    public OrderDto(String orderId,  String subCategory, String description,Double total, List<OrderDetailDto> list) {
        this.orderId = orderId;
        this.subCategory = subCategory;
        this.description = description;
        this.total=total;
        this.list = list;

    }

    public OrderDto(String orderId, String date, String subCategory, String status) {
        this.orderId = orderId;
        this.date = date;
        this.subCategory = subCategory;
        this.status = status;
    }

    private List<OrderDetailDto> list;
}
