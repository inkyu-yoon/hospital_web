package hospital.web.controller;

import hospital.web.domain.Response;
import hospital.web.domain.dto.hospital.HospitalInfoResponse;
import hospital.web.domain.entity.Hospital;
import hospital.web.exception.ErrorCode;
import hospital.web.exception.HospitalReviewAppException;
import hospital.web.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/hospitals")
@RequiredArgsConstructor
public class HospitalRestController {
    private final HospitalService hospitalService;


    @GetMapping("/info/{hospitalName}")
    public Response<List<HospitalInfoResponse>> getInfoOneByName(@PathVariable(name = "hospitalName") String hospitalName) {
        List<Hospital> foundHospitals = hospitalService.getByName(hospitalName);
        if (foundHospitals.size() == 0) {
            throw new HospitalReviewAppException(ErrorCode.NOT_FOUNDED, "입력하신 이름과 일치하는 병원이 존재하지 않습니다");
        }
        List<HospitalInfoResponse> hospitals = foundHospitals.stream().map(hospital -> new HospitalInfoResponse(hospital)).collect(Collectors.toList());
        return Response.success(hospitals);
    }
}
