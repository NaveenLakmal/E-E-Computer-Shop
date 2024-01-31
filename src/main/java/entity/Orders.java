package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Orders {
    @Id
    private String orderId;
    private String date;
    //private String custId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String category;
    private String subCategory;
    private String description;



    @OneToMany(mappedBy = "orders")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Orders(String orderId, String date,String category,String description,String subCategory) {
        this.orderId = orderId;
        this.date = date;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
    }
}
