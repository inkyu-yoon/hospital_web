package hospital.web.domain.dto;


import hospital.web.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private String userName;
    private String email;

    public UserJoinResponse(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();

    }
}
