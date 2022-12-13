package hospital.web.controller;

import hospital.web.domain.dto.hospital.HospitalDetailsDto;
import hospital.web.domain.dto.hospital.HospitalListDto;
import hospital.web.domain.dto.review.ReviewShow;
import hospital.web.domain.entity.Hospital;
import hospital.web.service.HospitalService;
import hospital.web.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor
@Slf4j
public class HospitalController {

    private final HospitalService hospitalService;
    private final ReviewService reviewService;


    @GetMapping("")
    public String searchList(@RequestParam(required = false) String keyword, @RequestParam(required = false) String condition, Model model, Pageable pageable) {
        log.info("검색 조건 : {}", condition);
        Page<Hospital> hospitals = hospitalService.getHospitalListAll(keyword, pageable);
        if (condition != null) {
            if (condition.equals("hospitalName")) {
                hospitals = hospitalService.getHospitalListPageByName(keyword, pageable);
            } else if (condition.equals("roadName")) {
                hospitals = hospitalService.getHospitalListPageByRoadName(keyword, pageable);
            }
        }
        Page<HospitalListDto> hospitalListDtos = hospitals.map(hospital -> new HospitalListDto(hospital));
        log.info("키워드:{}", keyword);


        model.addAttribute("keyword", keyword);
        model.addAttribute("condition", condition);
        model.addAttribute("hospitals", hospitalListDtos);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "hospitals/search";
    }

    @GetMapping("/{id}/details")
    public String showReviews(@PathVariable("id") Long id, Model model) {
        Hospital hospital = hospitalService.getById(id);
        List<ReviewShow> reviews = reviewService.getReviewsByHospitalId(id).stream().map(review -> new ReviewShow(review, review.getUser().getUserAccount())).collect(Collectors.toList());


        HospitalDetailsDto hospitalDetailsDto = new HospitalDetailsDto(hospital, reviews);
        model.addAttribute("hospital", hospitalDetailsDto);
        return "hospitals/details";

    }


}
