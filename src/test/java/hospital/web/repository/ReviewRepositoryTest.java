package hospital.web.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    HospitalRepository hospitalRepository;

    @Test
    public void test() {
        Long id = reviewRepository.findById(1L).get().getHospital().getId();
        System.out.println("id = " + id);
    }

}