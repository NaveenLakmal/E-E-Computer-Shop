package dto;

import entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private String userId;
    private String userName;
    private String password;
    private String userType;
    //private User user;

    public UserDto(String userName, String password, String userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public UserDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
