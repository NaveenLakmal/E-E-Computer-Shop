package dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderTm {

    private String itemCode;
    private String subCategory;
    private String description;
    private JFXButton btn;
}
