package dto.tm;

import com.jfoenix.controls.JFXButton;
import dto.OrderDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemTm {
    private String itemCode;
    private String name;

    private double price;
    private JFXButton btn;

}
