package hospital.web.controller;

import hospital.web.domain.Response;
import hospital.web.domain.dto.join.UserJoinRequest;
import hospital.web.domain.dto.join.UserJoinResponse;
import hospital.web.domain.dto.login.UserLoginRequest;
import hospital.web.domain.dto.login.UserLoginResponse;
import hospital.web.domain.dto.review.ReviewInfoResponse;
import hospital.web.domain.dto.review.ReviewInfoResponseFromUser;
import hospital.web.domain.dto.review.ReviewShow;
import hospital.web.domain.entity.User;
import hospital.web.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {

        User join = userService.join(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));
        UserJoinResponse userJoinResponse = new UserJoinResponse(join);
        return Response.success(userJoinResponse);

    }
/**
 {
 "userAccount":"MyId",
 "password":"12345",
 "userName":"윤인규",
 "email":"Myemail@email.com",
 "phone":"000-000-0000"

 }
 **/
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest.getUserAccount(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/{userAccount}/reviews")
    public Response<List<ReviewInfoResponseFromUser>> showReviewsByUserAccount(@PathVariable String userAccount) {
        List<ReviewInfoResponseFromUser> reviews = userService.getUserByUserAccount(userAccount).getReviews().stream().map(review -> new ReviewInfoResponseFromUser(review)).collect(Collectors.toList());
        return Response.success(reviews);
    }


}
