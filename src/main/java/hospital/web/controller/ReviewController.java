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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private final HospitalService hospitalService;
    private final ReviewService reviewService;

    @PostMapping
    public Response<ReviewCreateResponse> write(@RequestBody ReviewCreateRequest reviewCreateRequest){
        Review review = new Review(reviewCreateRequest,hospitalService.getById(reviewCreateRequest.getHospitalId()).get());
        reviewService.createReview(review);
        ReviewCreateResponse reviewCreateResponse = new ReviewCreateResponse(review, "리뷰 등록 성공");
        return Response.success(reviewCreateResponse);
    }
}
