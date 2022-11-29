package hospital.web.domain.entity;


import hospital.web.domain.dto.UserJoinRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;
    private String password;
    private String phone;
    private String email;

    public User(String userName, String password, String phone, String email) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public User(UserJoinRequest userJoinRequest) {
        this.userName = userJoinRequest.getUserName();
        this.password = userJoinRequest.getPassword();
        this.phone = userJoinRequest.getPhone();
        this.email = userJoinRequest.getEmail();
    }
}
