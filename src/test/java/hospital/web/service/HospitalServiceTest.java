package hospital.web.service;

import hospital.web.domain.entity.Hospital;
import hospital.web.parser.ReadData;
import hospital.web.repository.HospitalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class HospitalServiceTest {

    @Autowired
    HospitalService hospitalService;
    @Autowired
    HospitalRepository hospitalRepository;

    @Test
    void insertTest() throws IOException {
        long count = hospitalRepository.count();
        System.out.println(count);
    }

}