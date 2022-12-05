package hospital.web.domain.entity;


import hospital.web.domain.dto.UserJoinRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id",nullable = false,unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_Role")
    private UserRole userRole;

    public User(String userId, String password, String userName, String phone, String email) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.userRole = UserRole.USER;

    }

    public User(UserJoinRequest userJoinRequest) {
        this.userId = userJoinRequest.getUserName();
        this.password = userJoinRequest.getPassword();
        this.userName = userJoinRequest.getUserName();
        this.phone = userJoinRequest.getPhone();
        this.email = userJoinRequest.getEmail();
        this.userRole = UserRole.USER;
    }
}
