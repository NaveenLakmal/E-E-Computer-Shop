package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Item {
    @Id
    private String itemCode;
    private String category;
    private String subCategory;
    private String description;

    public Item(String itemCode, String category, String subCategory, String description) {
        this.itemCode = itemCode;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
    }
}
