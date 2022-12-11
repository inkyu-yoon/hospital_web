package hospital.web.controller;

import hospital.web.domain.dto.join.UserJoinRequestByForm;
import hospital.web.domain.entity.User;
import hospital.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/join")
    public String join(){
        return "users/join";
    }

    @PostMapping("/join")
    public String join(UserJoinRequestByForm userJoinRequestByForm) {
        String encodedPassword = encoder.encode(userJoinRequestByForm.getPassword());
        userService.join(userJoinRequestByForm.toEntity(encodedPassword));
        return "redirect:/hospitals/" ;
    }


    @GetMapping("/login")
    public String login(){
        return "users/login";
    }

}
