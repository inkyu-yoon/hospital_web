package hospital.web.domain.dto;


import hospital.web.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequest {
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phone;

    public User toEntity(String password) {
        return new User(this.userId, password, this.userName, this.phone, this.email);

    }
}
