package hospital.web;

import hospital.web.domain.Hospital;
import hospital.web.parser.HospitalParser;
import hospital.web.parser.ReadData;
import hospital.web.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class WebApplication {


	public static void main(String[] args) throws IOException {
//		SpringApplication.run(WebApplication.class, args);
		HospitalService hospitalService = new HospitalService();
		hospitalService.insertData();
	}


}
