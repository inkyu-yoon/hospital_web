package hospital.web.controller;

import hospital.web.domain.dto.HospitalDetailsDto;
import hospital.web.domain.dto.review.ReviewCreateByForm;
import hospital.web.domain.dto.review.ReviewCreateRequest;
import hospital.web.domain.entity.Comment;
import hospital.web.domain.entity.Hospital;
import hospital.web.domain.entity.Review;
import hospital.web.domain.entity.User;
import hospital.web.service.HospitalService;
import hospital.web.service.ReviewService;
import hospital.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final BCryptPasswordEncoder encoder;

    @PostMapping("")
    public String createReview(ReviewCreateByForm reviewCreateByForm, Model model) {
        Hospital hospital = hospitalService.getById(reviewCreateByForm.getHospitalId()).get();
        log.info("{}{}",reviewCreateByForm.getUserAccount(),reviewCreateByForm.getPassword());
        User user = userService.getUserByUserAccount(reviewCreateByForm.getUserAccount());
        if (user == null) {
            model.addAttribute("message", String.format("%s ID는 존재하지 않습니다.", reviewCreateByForm.getUserAccount()));
            return "posts/error";
        }
        log.info("{}",user.getPassword());
        log.info("{}",encoder.matches(reviewCreateByForm.getPassword(), user.getPassword()));

        if (encoder.matches(reviewCreateByForm.getPassword(), user.getPassword())) {

            Review review = new Review(reviewCreateByForm, hospital, user);
            reviewService.createReview(review);
            return "redirect:/hospitals/" + reviewCreateByForm.getHospitalId() + "/details";
        }
        return "redirect:/hospitals/"+reviewCreateByForm.getHospitalId()+"/details";

    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, String userAccount, String password) {
        User user = userService.getUserByUserAccount(userAccount);

        if (!encoder.matches(password, user.getPassword())) {
            return "posts/error";
        }
        Review review = reviewService.findOne(id);
        Long hospitalId = review.getHospital().getId();
        reviewService.deleteOne(id);
        return "redirect:/hospitals/"+hospitalId+"/details";
    }
}
