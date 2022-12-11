package hospital.web.controller;

import hospital.web.domain.Response;
import hospital.web.domain.dto.review.ReviewCreateRequest;
import hospital.web.domain.dto.review.ReviewCreateResponse;
import hospital.web.domain.entity.Review;
import hospital.web.service.HospitalService;
import hospital.web.service.ReviewService;
import hospital.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
@Slf4j
public class ReviewRestController {

    private final HospitalService hospitalService;
    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping
    public Response<ReviewCreateResponse> write(@RequestBody ReviewCreateRequest reviewCreateRequest, Authentication authentication) {
        Review review = new Review(reviewCreateRequest, hospitalService.getById(reviewCreateRequest.getHospitalId()).get(), userService.getUserByUserAccount(reviewCreateRequest.getUserAccount()));
        if (authentication.isAuthenticated()) {
            reviewService.createReview(review);
        }
        ReviewCreateResponse reviewCreateResponse = new ReviewCreateResponse(review, "리뷰 등록 성공");

        return Response.success(reviewCreateResponse);
    }
    /**
     {
     "title":"리뷰 제목입니다.",
     "content":"리뷰 내용입니다.",
     "userAccount" : "MyId",
     "hospitalId": 1
     }
     */
}
