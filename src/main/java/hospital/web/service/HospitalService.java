package hospital.web.service;

import hospital.web.domain.entity.Hospital;
import hospital.web.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public Page<Hospital> getHospitalListPage(String keyword , Pageable pageable) {
        Page<Hospital> hospitals;
        if (keyword == null) {
            hospitals = hospitalRepository.findAll(pageable);
        } else {
            hospitals = hospitalRepository.findByRoadNameAddressContaining(keyword, pageable);
        }
        return hospitals;
    }

    public Optional<Hospital> getById(Long id) {
        Optional<Hospital> foundHospital = hospitalRepository.findById(id);
        return foundHospital;
    }

}
