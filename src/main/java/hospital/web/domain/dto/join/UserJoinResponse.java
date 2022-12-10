package hospital.web.domain.dto.join;


import hospital.web.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private String userAccount;
    private String userName;

    public UserJoinResponse(User user) {
        this.userAccount = user.getUserAccount();
        this.userName = user.getUserName();
    }
}
