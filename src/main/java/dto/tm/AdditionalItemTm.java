package dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdditionalItemTm {
    private String itemCode;
    private String name;
    private double price;
    private JFXButton btn;

}
