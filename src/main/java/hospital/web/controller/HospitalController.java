package hospital.web.controller;

import hospital.web.domain.dto.HospitalListDto;
import hospital.web.domain.dto.HospitalReviewsDto;
import hospital.web.domain.entity.Hospital;
import hospital.web.service.HospitalService;
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

@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor
@Slf4j
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("")
    public String searchList(@RequestParam(required = false) String keyword, Model model, Pageable pageable) {
        Page<Hospital> hospitals = hospitalService.getHospitalListPage(keyword, pageable);
        Page<HospitalListDto> hospitalListDtos = hospitals.map(hospital -> new HospitalListDto(hospital));
        log.info("키워드:{}", keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("hospitals", hospitalListDtos);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "hospitals/search";
    }

    @GetMapping("/{id}/reviews")
    public String showReviews(@PathVariable("id") Long id, Model model) {
        Hospital hospital = hospitalService.getById(id).get();
        HospitalReviewsDto hospitalReviewsDto = new HospitalReviewsDto(hospital);
        model.addAttribute("hospital", hospitalReviewsDto);
        return "hospitals/reviews";

    }
}
