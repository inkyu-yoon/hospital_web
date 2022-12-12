package hospital.web.domain.entity;


import hospital.web.domain.dto.join.UserJoinRequest;
import hospital.web.domain.dto.join.UserJoinRequestByForm;
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
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_account",nullable = false,unique = true)
    private String userAccount;

    @Column(nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;
    private String phone;
    private String email;



    @Enumerated(EnumType.STRING)
    @Column(name = "user_Role")
    private UserRole userRole;

    public User(String userAccount, String password, String userName, String phone, String email) {
        this.userAccount = userAccount;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.userRole = UserRole.USER;

    }

    public User(UserJoinRequestByForm userJoinRequestByForm) {
        this.userAccount = userJoinRequestByForm.getUserAccount();
        this.password = userJoinRequestByForm.getPassword();
        this.userName = userJoinRequestByForm.getUserName();
        this.phone = userJoinRequestByForm.getPhone();
        this.email = userJoinRequestByForm.getEmail();
        this.userRole = UserRole.USER;

    }

    public User(UserJoinRequest userJoinRequest) {
        this.userAccount = userJoinRequest.getUserAccount();
        this.password = userJoinRequest.getPassword();
        this.userName = userJoinRequest.getUserName();
        this.phone = userJoinRequest.getPhone();
        this.email = userJoinRequest.getEmail();
        this.userRole = UserRole.USER;
    }
}
