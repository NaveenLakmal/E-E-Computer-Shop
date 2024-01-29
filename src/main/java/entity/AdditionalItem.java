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

    public AdditionalItem(String itemCode, String name, double price) {
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
    }
}
