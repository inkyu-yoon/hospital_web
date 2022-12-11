package hospital.web.domain.dto.join;


import hospital.web.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserJoinRequestByForm {
    private String userAccount;
    private String password;
    private String userName;
    private String email;
    private String phone;

    public User toEntity(String password) {
        return new User(this.userAccount, password, this.userName, this.phone, this.email);
    }
}
