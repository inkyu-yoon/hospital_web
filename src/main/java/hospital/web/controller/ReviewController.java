package hospital.web.controller;

import hospital.web.domain.dto.HospitalDetailsDto;
import hospital.web.domain.dto.review.ReviewCreateByForm;
import hospital.web.domain.dto.review.ReviewCreateRequest;
import hospital.web.domain.entity.Hospital;
import hospital.web.domain.entity.Review;
import hospital.web.domain.entity.User;
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

    @PostMapping("")
    public String createReview(ReviewCreateByForm reviewCreateByForm) {
        Hospital hospital = hospitalService.getById(reviewCreateByForm.getHospitalId()).get();
        User user = userService.getUserByUserAccount(reviewCreateByForm.getUserAccount());
        Review review = new Review(reviewCreateByForm, hospital,user);
        reviewService.createReview(review);
        return "redirect:/hospitals/"+reviewCreateByForm.getHospitalId()+"/details";
    }
}
