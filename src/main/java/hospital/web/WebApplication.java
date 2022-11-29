package hospital.web;

import hospital.web.domain.entity.Hospital;
import hospital.web.parser.ReadData;
import hospital.web.repository.HospitalJpaRepository;
import hospital.web.repository.HospitalRepository;
import hospital.web.service.HospitalService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class WebApplication {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(WebApplication.class, args);
		HospitalJpaRepository hospitalJpaRepository = new HospitalJpaRepository();
		hospitalJpaRepository.saveOneByPureJpa("hospitalData.txt");

	}
}
