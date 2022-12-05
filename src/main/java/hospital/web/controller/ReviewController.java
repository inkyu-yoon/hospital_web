package hospital.web.controller;

import hospital.web.domain.Response;
import hospital.web.domain.dto.ReviewCreateRequest;
import hospital.web.domain.dto.ReviewCreateResponse;
import hospital.web.domain.dto.UserJoinResponse;
import hospital.web.domain.entity.Review;
import hospital.web.repository.HospitalRepository;
import hospital.web.repository.ReviewRepository;
import hospital.web.service.HospitalService;
import hospital.web.service.ReviewService;
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
public class ReviewController {

    private final HospitalService hospitalService;
    private final ReviewService reviewService;

    @PostMapping
    public Response<ReviewCreateResponse> write(@RequestBody ReviewCreateRequest reviewCreateRequest , Authentication authentication){
        Review review = new Review(reviewCreateRequest,hospitalService.getById(reviewCreateRequest.getHospitalId()).get());
        reviewService.createReview(review);
        ReviewCreateResponse reviewCreateResponse = new ReviewCreateResponse(review, "리뷰 등록 성공");
        log.info("Controller user : {}", authentication.getName());

        return Response.success(reviewCreateResponse);
    }
    /**
     {
     "title":"hi",
     "content":"12345",
     "userId" : "잉규",
     "hospitalId": 1

     }
     */
}
