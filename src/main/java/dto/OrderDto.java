package dto;

import java.util.List;

public class OrderDto {
    private String orderId;
    private String date;
    private String custId;

    public OrderDto(String orderId, String date, String custId) {
        this.orderId = orderId;
        this.date = date;
        this.custId = custId;
    }

    //private List<OrderDetailDto> list;
}