package hospital.web.domain.dto;


import hospital.web.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String password;
    private String email;
    private String phone;

    public User toEntity(String password) {
        return new User(this.userName, password, this.phone, this.email);

    }
}
