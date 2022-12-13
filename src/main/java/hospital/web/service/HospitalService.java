package hospital.web.service;

import hospital.web.domain.entity.Hospital;
import hospital.web.exception.ErrorCode;
import hospital.web.exception.HospitalReviewAppException;
import hospital.web.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public Page<Hospital> getHospitalListPageByRoadName(String keyword , Pageable pageable) {
        Page<Hospital> hospitals;
        if (keyword == null) {
            hospitals = hospitalRepository.findAll(pageable);
        } else {
            hospitals = hospitalRepository.findByRoadNameAddressContaining(keyword, pageable);
        }
        return hospitals;
    }

    public Page<Hospital> getHospitalListAll(String keyword, Pageable pageable) {
        return hospitalRepository.findAll(pageable);
    }
    public Page<Hospital> getHospitalListPageByName(String keyword , Pageable pageable) {
        Page<Hospital> hospitals;
        if (keyword == null) {
            hospitals = hospitalRepository.findAll(pageable);
        } else {
            hospitals = hospitalRepository.findByHospitalNameContaining(keyword, pageable);
        }
        return hospitals;
    }

    public Hospital getById(Long id) {
        Hospital foundHospital = hospitalRepository.findById(id).orElseThrow(
                () -> new HospitalReviewAppException(ErrorCode.NOT_FOUNDED, "ID에 해당하는 병원이 존재하지 않습니다.")
        );
        return foundHospital;
    }
    public List<Hospital> getByName(String hospitalName) {
        List<Hospital> foundHospitals = hospitalRepository.findByHospitalName(hospitalName);
        return foundHospitals;
    }
}
