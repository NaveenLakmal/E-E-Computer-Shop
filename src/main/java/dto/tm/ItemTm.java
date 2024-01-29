package dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemTm {
    private String iCode;
    private String category;
    private String subCategory;
    private String description;
    private JFXButton btn;

}
