package hospital.web.repository;

import hospital.web.domain.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Page<Hospital> findByRoadNameAddressContaining(String address, Pageable pageable);
    Page<Hospital> findByHospitalNameContaining(String hospitalName, Pageable pageable);

    List<Hospital> findByHospitalName(String hospitalName);



}
