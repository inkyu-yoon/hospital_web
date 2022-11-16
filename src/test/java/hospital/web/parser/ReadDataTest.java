package hospital.web.parser;

import hospital.web.domain.Hospital;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ReadDataTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("데이터 읽어오기 테스트")
    void 데이터읽기() throws IOException {
        ReadData readData = new ReadData();
        List<Hospital> hospitals = readData.ReadLine("hospitalData.txt");
        for (Hospital hospital : hospitals) {
            System.out.println(hospital.getHospitalName());
        }
    }

    @Test
    @DisplayName("데이터 입력 테스트")
    @Transactional
    @Rollback(value = false)
    void 데이터입력() throws IOException {
        ReadData readData = new ReadData();
        List<Hospital> hospitals = readData.ReadLine("hospitalData.txt");
        for (Hospital hospital : hospitals) {
        em.persist(hospital);
        }


    }
}