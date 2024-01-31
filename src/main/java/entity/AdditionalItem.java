package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AdditionalItem {
    @Id
    private String itemCode;
    private String name;
    private double price;
    private  int qty;

    public AdditionalItem(String itemCode, String name,int qty, double price) {
        this.itemCode = itemCode;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }
}
