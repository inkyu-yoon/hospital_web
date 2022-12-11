package hospital.web.controller;

import hospital.web.domain.dto.HospitalDetailsDto;
import hospital.web.domain.dto.review.ReviewCreateRequest;
import hospital.web.domain.entity.Hospital;
import hospital.web.domain.entity.Review;
import hospital.web.service.HospitalService;
import hospital.web.service.ReviewService;
import hospital.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final UserService userService;
    private final HospitalService hospitalService;
    private final ReviewService reviewService;

    @PostMapping("/create")
    public String createReview(ReviewCreateRequest reviewCreateRequest) {
        log.info("{},{},{},{}",reviewCreateRequest.getTitle(),reviewCreateRequest.getContent(),reviewCreateRequest.getUserAccount(),reviewCreateRequest.getHospitalId());
        Hospital hospital = hospitalService.getById(reviewCreateRequest.getHospitalId()).get();
        Review review = new Review(reviewCreateRequest, hospital, userService.getUserByUserAccount(reviewCreateRequest.getUserAccount()));

        reviewService.createReview(review);

        HospitalDetailsDto hospitalDetailsDto = new HospitalDetailsDto(hospital);

        return "/hospitals/search";

    }
}
