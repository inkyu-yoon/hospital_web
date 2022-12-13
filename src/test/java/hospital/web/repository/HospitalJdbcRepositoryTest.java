package hospital.web.repository;

import hospital.web.domain.entity.Hospital;
import hospital.web.parser.ReadData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HospitalJdbcRepositoryTest {

    @Autowired
    HospitalJdbcRepository hospitalJdbcRepository;

    @Test
    @DisplayName("batchupdate 테스트")
    @Transactional
    @Rollback(value = false)
    void test() throws IOException {
        ReadData readData = new ReadData();
        List<Hospital> hospitals = readData.readLine("hospitalData.txt");
        hospitalJdbcRepository.insertHospitalList(hospitals);

    }
}